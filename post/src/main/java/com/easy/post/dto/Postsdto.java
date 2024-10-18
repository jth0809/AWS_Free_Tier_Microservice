package com.easy.post.dto;

public record Postsdto(
    String postId,
    Long memberId,
    String title,
    Long likeCount
){}
