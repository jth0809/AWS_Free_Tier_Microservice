package com.easy.post.dto;

public record AndroidComment(
    String commentId,
    String memberId,
    String content,
    String timestamp
) {
}
