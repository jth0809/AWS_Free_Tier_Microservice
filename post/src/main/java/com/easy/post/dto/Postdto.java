package com.easy.post.dto;

import java.util.List;

import com.easy.post.domain.Comment;

public record Postdto(
    String postId,
    Long memberId,
    String title,
    String content,
    Long likeCount,
    Boolean isLiked,
    List<Comment> comments
){}