package com.easy.post.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.easy.post.domain.Post;

public interface PostRepository extends MongoRepository<Post, String> {
    boolean existsByPostIdAndPostLikesMemberId(String postId, String memberId);
    
    void deleteByPostIdAndMemberId(String postId, String memberId);
    void deleteByCommentsMemberId(String memberId);
    void deleteByPostLikesMemberId(String memberId);
}
