package com.easy.edge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easy.edge.dto.Alldto;
import com.easy.edge.dto.PostWriteDto;

import reactor.core.publisher.Mono;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;


@Service
public class RequestService {
    private final StreamBridge streamBridge;

    private final Logger log = LoggerFactory.getLogger(RequestService.class);

    public RequestService(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public Mono<String> getMemberId() {
        return ReactiveSecurityContextHolder.getContext()
        .map(securityContext -> {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
                throw new IllegalStateException("Authentication not found or is not a JWT");
            }
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getClaim("username").toString();
        });
    }

    public void publishEvent(Alldto alldto) {
        log.info("Publishing orchestration event: {}", alldto);
        streamBridge.send("orchestration-out-0",  alldto);
        log.info("orchestration event published: {}", alldto);
    }

    public Mono<Void> publishPostEvent(PostWriteDto alldto) {
        return getMemberId().flatMap(memberId -> {
            PostWriteDto postWriteDto = new PostWriteDto(alldto.postId(), memberId, alldto.commentId(), alldto.title(), alldto.content(), alldto.date());
            log.info("Publishing post event: {}", postWriteDto);
            streamBridge.send("postWrite-out-0",  postWriteDto);
            log.info("post event published: {}", postWriteDto);
            return Mono.empty();
        });
    }
}
