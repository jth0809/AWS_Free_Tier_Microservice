package com.easy.post.service;

import org.springframework.stereotype.Service;
import com.easy.post.repository.PostRepository;
import com.easy.post.domain.Post;
import com.easy.post.dto.PostWriteDto;
import com.easy.post.dto.Postdto;
import com.easy.post.dto.Postsdto;
import com.easy.post.domain.PostLike;
import com.easy.post.domain.Comment;
import com.easy.post.domain.Content;



import java.util.stream.Collectors;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    
    public List<Postsdto> getPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> new Postsdto(
                        post.getPostId(),
                        post.getMemberId(),
                        post.getTitle(),
                        (long) post.getPostLikes().size()
                ))
                .collect(Collectors.toList());
    }
    public Postdto getPost(String postId) {
        return postRepository.findById(postId)
                .map(p -> new Postdto(
                        p.getPostId(),
                        p.getMemberId(),
                        p.getTitle(),
                        p.getContent().getContent(),
                        (long) p.getPostLikes().size(),
                        false,
                        p.getComments()
                ))
                .orElse(null);
    }

    public void addPost(PostWriteDto postwWriteDto) {
        Post post = new Post();
        post.setMemberId(postwWriteDto.memberId());
        post.setTitle(postwWriteDto.title());
        post.setContent(new Content(postwWriteDto.content()));
        
        save(post);
    }
    public void updatePost(String postId, PostWriteDto postWriteDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        
        if (!post.getMemberId().equals(postWriteDto.memberId())) {
            throw new IllegalArgumentException("Member does not have permission to update this post");
        }
        
        post.setTitle(postWriteDto.title());
        post.setContent(new Content(postWriteDto.content()));
        
        save(post);
    }
    public void deletePost(String postId, Long memberId) {
        postRepository.deleteByPostIdAndMemberId(postId, memberId);
    }


    private void deleteLike(Post post, Long memberId) {
        post.getPostLikes().removeIf(postLike -> postLike.getMemberId().equals(memberId));
    }
    private void addLike(Post post, Long memberId) {
        post.getPostLikes().add(new PostLike(memberId));
    }
    public void likePost(String postId, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
    
        if (!isLiked(postId, memberId)) {
            addLike(post, memberId);
        } else {
            deleteLike(post, memberId);
        }
        save(post);
    }
    public boolean isLiked(String postId, Long memberId) {
        return postRepository.existsByPostIdAndPostLikesMemberId(postId, memberId);
    }


    public void addComment(String postId, Long memberId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        
        post.getComments().add(new Comment(memberId, content));
        
        save(post);
    }
    public void updateComment(String postId, Long memberId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        
        post.getComments().stream()
                .filter(comment -> comment.getMemberId().equals(memberId))
                .findFirst()
                .ifPresent(comment -> comment.setContent(content));
        
        save(post);
    }
    public void deleteComment(String postId, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        
        post.getComments().removeIf(comment -> comment.getMemberId().equals(memberId));
        
        save(post);
    }


    public void save(Post post) {
        postRepository.save(post);
    }

    

}
