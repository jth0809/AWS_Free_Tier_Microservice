package com.easy.post.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostLike {

    private String memberId;

    public PostLike(String memberId) {
        this.memberId = memberId;
    }
}
