package com.easy.edge.controller;

import com.easy.edge.adapter.PostAdapter;
import com.easy.edge.dto.Alldto;
import com.easy.edge.dto.AndroidPost;
import com.easy.edge.dto.PostWriteDto;
import com.easy.edge.service.RequestService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.RedirectView;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
public class RequestControlloer {
    
    RequestService requestService;

    public RequestControlloer(RequestService requestService) {
        this.requestService = requestService;
    }
    @GetMapping("/logoutpage")
    public String logoutpage() {
        return  "<html>" +
                "<head><title>앱 열기</title></head>" +
                "<body>" +
                "<h1>로그아웃 성공! 안드로이드 앱을 열고 있습니다...</h1>" +
                "<script>" +
                "window.location.href = `easytrip://callback`;" +
                "</script>" +
                "</body>" +
                "</html>";
    }
    

    @GetMapping("/auth")
    public Mono<String> openAppPage(ServerWebExchange exchange) {
        AtomicReference<String> sessionId = new AtomicReference<>(null);
        exchange.getRequest().getCookies().forEach((name, cookies) -> {
            if ("SESSION".equals(name)) {
                sessionId.set(cookies.get(0).getValue());
            }
        });

        String script;
        if (sessionId.get() != null) {
            script = "<script>" +
                     "const sessionId = '" + sessionId.get() + "';" +
                     "window.location.href = `easytrip://callback?sessionId=${sessionId}`;" +
                     "</script>";
        } else {
            script = "<script>" +
                     "alert('세션이 존재하지 않습니다.');" +
                     "</script>";
        }

        return Mono.just("<html>" +
                         "<head><title>앱 열기</title></head>" +
                         "<body>" +
                         "<h1>안드로이드 앱을 열고 있습니다...</h1>" +
                         script +
                         "</body>" +
                         "</html>");
    }


    @PostMapping("/posts")
    public Mono<Void> orchestration(@RequestBody Alldto alldto) {
        requestService.publishEvent(alldto);
        return Mono.empty();
    }

    @GetMapping("/test/test6")
    public Mono<String> postMethodName(AndroidPost androidPost) {
        return requestService.publishPostEvent(PostAdapter.toPostWriteDto(androidPost)).then(Mono.just("Post added"));
    }
    
    
}
