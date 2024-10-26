package com.easy.messaging;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.easy.post.dto.PostWriteDto;
import com.easy.post.service.ReactivePostService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class PostFunctions {
    private static final Logger log = LoggerFactory.getLogger(PostFunctions.class);

    @Bean
    public Consumer<Flux<PostWriteDto>> postCreate(ReactivePostService reactivePostService) {
        return postWriteDtoFlux -> postWriteDtoFlux
                .doOnNext(postWriteDto -> log.info("Received new post: {}", postWriteDto))
                .flatMap(reactivePostService::addPost)
                .subscribe();
    }
    @Bean
    public Consumer<Flux<PostWriteDto>> postUpdate(ReactivePostService reactivePostService) {
        return postUpdateFlux -> postUpdateFlux
                .doOnNext(postdto -> log.info("Received update post request for post: {}", postdto))
                .flatMap(reactivePostService::updatePost)
                .subscribe();
    }

    @Bean
    public Consumer<Flux<PostWriteDto>> postDelete(ReactivePostService reactivePostService) {
        return postDeleteFlux -> postDeleteFlux
                .doOnNext(postId -> log.info("Received delete post request for post: {}", postId))
                .flatMap(reactivePostService::deletePost)
                .subscribe();
    }

    @Bean
    public Consumer<Flux<PostWriteDto>> commentCreate(ReactivePostService reactivePostService) {
        return commentCreateFlux -> commentCreateFlux
                .doOnNext(postdto -> log.info("Received new comment request for post: {}", postdto))
                .flatMap(reactivePostService::addComment)
                .subscribe();
    }

    @Bean
    public Consumer<Flux<PostWriteDto>> commentUpdate(ReactivePostService reactivePostService) {
        return commentUpdateFlux -> commentUpdateFlux
                .doOnNext(postdto -> log.info("Received update comment request for post: {}", postdto))
                .flatMap(reactivePostService::updateComment)
                .subscribe();
    }

    @Bean
    public Consumer<Flux<PostWriteDto>> commentDelete(ReactivePostService reactivePostService) {
        return commentDeleteFlux -> commentDeleteFlux
                .doOnNext(postdto -> log.info("Received delete comment request for post: {}", postdto))
                .flatMap(reactivePostService::deleteComment)
                .subscribe();
    }

    @Bean
    public Consumer<Mono<PostWriteDto>> likePost(ReactivePostService reactivePostService) {
        return likePostFlux -> reactivePostService.likePost(likePostFlux)
                .doOnNext(postdto -> log.info("Received like post request for post: {}", postdto))
                .subscribe();
    }
}
