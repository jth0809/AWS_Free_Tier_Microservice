package com.easy.post.dto;


public record PostWriteDto(
    String postId,
    Long memberId,
    String title,
    String content
) {}
