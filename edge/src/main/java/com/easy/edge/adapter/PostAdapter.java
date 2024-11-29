package com.easy.edge.adapter;

import org.springframework.stereotype.Component;

import com.easy.edge.dto.AndroidComment;
import com.easy.edge.dto.AndroidPost;
import com.easy.edge.dto.PostWriteDto;

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

    public AndroidComment toAndroidComment(PostWriteDto postWriteDto) {
        return new AndroidComment(
            postWriteDto.commentId(),
            postWriteDto.memberId(),
            postWriteDto.postId(),
            postWriteDto.content(),
            postWriteDto.date()
        );
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
    
    
}
