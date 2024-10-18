package com.easy.post.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.easy.post.domain.Post;

public interface PostRepository extends MongoRepository<Post, String> {
    boolean existsByPostIdAndPostLikesMemberId(String postId, Long memberId);
    
    void deleteByPostIdAndMemberId(String postId, Long memberId);
    void deleteByCommentsMemberId(Long memberId);
    void deleteByPostLikesMemberId(Long memberId);
}
