package com.easy.post.domain;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {

    @Id
    private String commentId;
    private String memberId;
    private String content;

    public Comment(String memberId, String content) {
        this.memberId = memberId;
        this.content = content;
    }

}
