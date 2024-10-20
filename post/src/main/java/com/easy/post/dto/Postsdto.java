package com.easy.post.dto;

public record Postsdto(
    String postId,
    String memberId,
    String title,
    Long likeCount
){}
