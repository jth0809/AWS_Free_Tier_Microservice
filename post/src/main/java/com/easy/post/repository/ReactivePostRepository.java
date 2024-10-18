package com.easy.post.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.easy.post.domain.Post;
import reactor.core.publisher.Mono;

public interface ReactivePostRepository extends ReactiveMongoRepository<Post, String> {
    Mono<Boolean> existsByPostIdAndPostLikesMemberId(String postId, Long memberId);
    
    Mono<Void> deleteByPostIdAndMemberId(String postId, Long memberId);
    Mono<Void> deleteByCommentsMemberId(Long memberId);
    Mono<Void> deleteByPostLikesMemberId(Long memberId);
    
}
