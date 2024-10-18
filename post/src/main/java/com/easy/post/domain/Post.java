package com.easy.post.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "posts")
public class Post {

    @Id
    private String postId;

    private Long memberId;
    
    private String title;

    private Content content;

    private List<Comment> comments = new ArrayList<>();

    private List<PostLike> postLikes = new ArrayList<>();
}
