package com.easy.member.dto;

public record Memberdto (
    String name,
    String email,
    Long followerCount,
    Long followingCount
){}
