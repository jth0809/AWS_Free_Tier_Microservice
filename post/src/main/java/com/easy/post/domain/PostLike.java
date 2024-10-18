package com.easy.post.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostLike {

    private Long memberId;

    public PostLike(Long memberId) {
        this.memberId = memberId;
    }
}
