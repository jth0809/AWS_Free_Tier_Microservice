package com.easy.post.dto;

import java.util.List;

import com.easy.post.domain.Comment;
import com.easy.post.domain.Content;


public record Postdto(
    String postId,
    String memberId,
    String title,
    Content content,
    Long likeCount,
    Boolean isLiked,
    List<Comment> comments
){}