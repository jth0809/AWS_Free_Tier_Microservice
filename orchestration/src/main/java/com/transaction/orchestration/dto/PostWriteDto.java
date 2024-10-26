package com.transaction.orchestration.dto;


public record PostWriteDto(
    String postId,
    String memberId,
    String commentId,
    String title,
    String content
) {}
