package com.easy.post.domain;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Comment {

    private Long memberId;
    private String content;

    public Comment(Long memberId, String content) {
        this.memberId = memberId;
        this.content = content;
    }

}
