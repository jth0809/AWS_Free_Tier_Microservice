package com.easy.post.dto;

public record AndroidPost(
    String id, 
    String memberId,
    String title,
    String content,
    String date
) {}
