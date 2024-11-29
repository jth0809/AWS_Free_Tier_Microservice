package com.easy.edge.dto;


public record PostWriteDto(
    String postId,
    String memberId,
    String commentId,
    String title,
    String content,
    String date
) {}
