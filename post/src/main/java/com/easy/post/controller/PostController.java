package com.easy.post.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.easy.post.dto.PostWriteDto;
import com.easy.post.dto.Postdto;
import com.easy.post.dto.Postsdto;
import com.easy.post.service.PostService;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class PostController {

    private PostService postService;


    @GetMapping("/post")
    List<Postsdto>getPostlist(){
        return postService.getPosts();
    }

    @GetMapping("/post/{postId}")
    Postdto getPost(@PathVariable String postId){
        return postService.getPost(postId);
    }
    

    @PostMapping("/post")
    public String addPost(@RequestBody PostWriteDto post){
        postService.addPost(post);
        return "Post added";
    }

    @PostMapping("/post/{postId}/comment")
    public String addComment(@PathVariable String postId, @RequestBody Long memberId, String comment){
        postService.addComment(postId, memberId, comment);
        return "Comment added";
    }
    
    @PostMapping("/post/{postId}/like")
    public String likePost(@PathVariable String postId, @RequestBody Long memberId){
        postService.likePost(postId, memberId);
        return "Post liked";
    }
    
    @PostMapping("/post/{postId}")
    public String updatePost(@PathVariable String postId, @RequestBody PostWriteDto post){
        postService.updatePost(postId, post);
        return "Post updated";
    }

    

}
