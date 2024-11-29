package com.easy.post.adapter;

import org.springframework.stereotype.Component;

import com.easy.post.dto.AndroidComment;
import com.easy.post.dto.AndroidPost;
import com.easy.post.dto.PostWriteDto;
import com.easy.post.dto.Postdto;
import com.easy.post.dto.Postsdto;
import com.easy.post.domain.Comment;
import com.easy.post.domain.Post;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class PostAdapter {

    public static  AndroidPost toAndroidPost(PostWriteDto postWriteDto) {
        return new AndroidPost(
            postWriteDto.postId(),
            postWriteDto.memberId(),
            postWriteDto.title(),
            postWriteDto.content(),
            postWriteDto.date()
        );
    }

    public static AndroidComment toAndroidComment(PostWriteDto postWriteDto) {
        return new AndroidComment(
            postWriteDto.commentId(),
            postWriteDto.memberId(),
            postWriteDto.content(),
            postWriteDto.date()
        );
    }

    public static PostWriteDto androidCommentToPostWriteDto(AndroidComment androidComment, String postId) {
        return new PostWriteDto(
            postId,
            androidComment.memberId(),
            androidComment.commentId(),
            null,
            androidComment.content(),
            androidComment.timestamp()
        );
    }

    public static List<AndroidComment> commentsdtoToAndroidComment(List<Comment> comments) {
        return comments.stream()
            .map(comment -> new AndroidComment(
                comment.getCommentId(),
                comment.getMemberId(),
                comment.getContent(),
                comment.getTimestamp()
            ))
            .collect(Collectors.toList());
    }

    public static PostWriteDto toPostWriteDto(AndroidPost androidPost) {
        return new PostWriteDto(
            androidPost.id(),
            androidPost.memberId(),
            null,
            androidPost.title(),
            androidPost.content(),
            androidPost.date()
        );
    }

    public static AndroidPost postdtoToAndroidPost(Postdto post) {
        return new AndroidPost(
            post.postId(),
            post.memberId(),
            post.title(),
            post.content().getContents(),
            post.content().getDate()
        );
    }

    public static AndroidPost postsdtoToAndroidPost(Postsdto post) {
        return new AndroidPost(
            post.postId(),
            post.memberId(),
            post.title(),
            post.content().getContents(),
            post.content().getDate()
        );
    }

    public static AndroidPost postToAndroidPost(Post post) {
        return new AndroidPost(
            post.getPostId(),
            post.getMemberId(),
            post.getTitle(),
            post.getContent().getContents(),
            post.getContent().getDate()
        );
    }
    
    
}
