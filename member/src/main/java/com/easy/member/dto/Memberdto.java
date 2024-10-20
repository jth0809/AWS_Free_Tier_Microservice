package com.easy.member.dto;

public record Memberdto (
    String id,
    String name,
    String email,
    Long followerCount,
    Long followingCount
){}
