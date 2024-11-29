package com.easy.post.dto;

import com.easy.post.domain.Content;

public record Postsdto(
    String postId,
    String memberId,
    String title,
    Content content,
    Long likeCount
){}
