package com.easy.post.domain;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Content {

    private String content;

    public Content(String content) {
        this.content = content;
    }
}
