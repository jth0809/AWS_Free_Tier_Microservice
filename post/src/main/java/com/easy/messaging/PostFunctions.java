package com.easy.messaging;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.easy.post.dto.PostWriteDto;
import com.easy.post.service.ReactivePostService;

import reactor.core.publisher.Mono;

@Configuration
public class PostFunctions {
    private static final Logger log = LoggerFactory.getLogger(PostFunctions.class);




    @Bean
    public Consumer<Mono<String>> postRead(ReactivePostService reactivePostService) {
        return postReadMono -> postReadMono
                .doOnNext(post -> log.info("Received read post request for post: {}", post))
                .flatMap(reactivePostService::getPost)
                .subscribe();
    }

    @Bean
    public Consumer<Mono<Void>> postsRead(ReactivePostService reactivePostService) {
        return postsReadMono -> postsReadMono
                .doOnNext(post -> log.info("Received read posts request"))
                .flatMapMany(post -> reactivePostService.getPosts())
                .subscribe();
    }

    @Bean
    public Consumer<Mono<PostWriteDto>> postCreate(ReactivePostService reactivePostService) {
        return postWriteDtoFlux -> postWriteDtoFlux
                .doOnNext(postWriteDto -> log.info("Received new post: {}", postWriteDto))
                .flatMap(reactivePostService::addPost)
                .subscribe();
    }
    @Bean
    public Consumer<Mono<PostWriteDto>> postUpdate(ReactivePostService reactivePostService) {
        return postUpdateFlux -> postUpdateFlux
                .doOnNext(postdto -> log.info("Received update post request for post: {}", postdto))
                .flatMap(reactivePostService::updatePost)
                .subscribe();
    }

    @Bean
    public Consumer<Mono<PostWriteDto>> postDelete(ReactivePostService reactivePostService) {
        return postDeleteFlux -> postDeleteFlux
                .doOnNext(postId -> log.info("Received delete post request for post: {}", postId))
                .flatMap(reactivePostService::deletePost)
                .subscribe();
    }

    @Bean
    public Consumer<Mono<PostWriteDto>> commentCreate(ReactivePostService reactivePostService) {
        return commentCreateFlux -> commentCreateFlux
                .doOnNext(postdto -> log.info("Received new comment request for post: {}", postdto))
                .flatMap(reactivePostService::addComment)
                .subscribe();
    }

    @Bean
    public Consumer<Mono<PostWriteDto>> commentUpdate(ReactivePostService reactivePostService) {
        return commentUpdateFlux -> commentUpdateFlux
                .doOnNext(postdto -> log.info("Received update comment request for post: {}", postdto))
                .flatMap(reactivePostService::updateComment)
                .subscribe();
    }

    @Bean
    public Consumer<Mono<PostWriteDto>> commentDelete(ReactivePostService reactivePostService) {
        return commentDeleteFlux -> commentDeleteFlux
                .doOnNext(postdto -> log.info("Received delete comment request for post: {}", postdto))
                .flatMap(reactivePostService::deleteComment)
                .subscribe();
    }

    @Bean
    public Consumer<Mono<PostWriteDto>> likePost(ReactivePostService reactivePostService) {
        return likePostFlux -> likePostFlux
                .doOnNext(postdto -> log.info("Received like post request for post: {}", postdto))
                .flatMap(reactivePostService::likePost)
                .subscribe();
    }
}
