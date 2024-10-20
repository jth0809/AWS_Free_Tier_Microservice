package com.easy.post.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.easy.post.domain.Post;
import reactor.core.publisher.Mono;

public interface ReactivePostRepository extends ReactiveMongoRepository<Post, String> {
    Mono<Boolean> existsByPostIdAndPostLikesMemberId(String postId, String memberId);
    
    Mono<Void> deleteByPostIdAndMemberId(String postId, String memberId);
    Mono<Void> deleteByCommentsMemberId(String memberId);
    Mono<Void> deleteByPostLikesMemberId(String memberId);
    
}
