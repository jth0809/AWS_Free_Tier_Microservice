package com.easy.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Memberdto {
    private Long id;
    private String name;
    private String email;
    private Long followerCount;
    private Long followingCount;
}
