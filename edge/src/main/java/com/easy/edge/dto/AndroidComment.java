package com.easy.edge.dto;

public record AndroidComment(
    String commentId,
    String memberId,
    String postId,
    String content,
    String timestamp
) {
}
