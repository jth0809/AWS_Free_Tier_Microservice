package com.easy.post.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.easy.post.adapter.PostAdapter;
import com.easy.post.dto.AndroidComment;
import com.easy.post.dto.AndroidPost;
import com.easy.post.dto.PostWriteDto;
import com.easy.post.dto.Postdto;
import com.easy.post.dto.Postsdto;
import com.easy.post.service.ReactivePostService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.easy.post.dto.PostWriteDto;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;




@RestController
public class PostController {

    private ReactivePostService postService;
    
    //private PostWriteDto postWriteDto = new PostWriteDto("memberId", "title", "content","title","content");

    public PostController(ReactivePostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post")
    public Mono<List<AndroidPost>> getPostlist() {
        return postService.getPosts()
            .map(postList -> postList.stream()
                .map(PostAdapter::postsdtoToAndroidPost)
                .collect(Collectors.toList())
            );
    }

    @GetMapping("/post/{postId}")
    Mono<AndroidPost> getPost(@PathVariable String postId){
        return postService.getPost(postId).flatMap(post -> {
            return Mono.just(PostAdapter.postdtoToAndroidPost(post));
        });
    }

    @PostMapping("/post")
    public Mono<AndroidPost> addPost(@RequestBody AndroidPost post) {
        return postService.addPost(PostAdapter.toPostWriteDto(post))
                    .flatMap(postdto -> {
                        return Mono.just(PostAdapter.postToAndroidPost(postdto));
                    });
    }

    /* 
    @PostMapping("/post/{postId}")
    public String updatePost(@PathVariable String postId, @RequestBody PostWriteDto post){
        PostWriteDto postdto = new PostWriteDto(postId, post.memberId(), null, post.title(), post.content());
        postService.updatePost(postdto);
        return "Post updated";
    }
    */
    @GetMapping("/post/{postId}/comment")
    public Mono<List<AndroidComment>> getcomments(@PathVariable String postId) {
        return postService.getPost(postId).flatMap(post -> {
            return Mono.just(PostAdapter.commentsdtoToAndroidComment(post.comments()));
        });
    }
    @PostMapping("/post/{postId}/comment")
    public Mono<String> addComment(@PathVariable String postId, @RequestBody AndroidComment comment) {
        return postService.addComment(PostAdapter.androidCommentToPostWriteDto(comment, postId))
                .then(Mono.just("Comment added"));
    }
    
    

}
