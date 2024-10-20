package com.easy.post.service;

import com.easy.post.domain.Comment;
import com.easy.post.domain.Content;
import com.easy.post.domain.Post;
import com.easy.post.domain.PostLike;
import com.easy.post.dto.PostWriteDto;
import com.easy.post.dto.Postdto;
import com.easy.post.dto.Postsdto;
import com.easy.post.repository.ReactivePostRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactivePostService {

    private final ReactivePostRepository postRepository;


    public ReactivePostService(ReactivePostRepository postRepository) {
        this.postRepository = postRepository;
    }
    
    public Flux<Postsdto> getPosts() {
        return postRepository.findAll()
                .map(post -> new Postsdto(
                        post.getPostId(),
                        post.getMemberId(),
                        post.getTitle(),
                        (long) post.getPostLikes().size()
                ));
    }
    public Mono<Postdto> getPost(String postId) {
        return postRepository.findById(postId)
                .map(p -> new Postdto(
                        p.getPostId(),
                        p.getMemberId(),
                        p.getTitle(),
                        p.getContent().getContent(),
                        (long) p.getPostLikes().size(),
                        false,
                        p.getComments()
                ));
    }

    public Mono<Void> addPost(PostWriteDto postWriteDto) {
        Post post = new Post();
        post.setMemberId(postWriteDto.memberId());
        post.setTitle(postWriteDto.title());
        post.setContent(new Content(postWriteDto.content()));
        
        return save(post);
    }
    public Mono<Void> updatePost(PostWriteDto postdto) {
        return postRepository.findById(postdto.postId())
                .flatMap(post -> {
                    if (!post.getMemberId().equals(postdto.memberId())) {
                        return Mono.error(new IllegalArgumentException("Member does not have permission to update this post"));
                    }
                    post.setTitle(postdto.title());
                    post.setContent(new Content(postdto.content()));
                    return save(post);
                });
    }

    public Mono<Void> deletePost(PostWriteDto postdto) {
        return postRepository.deleteByPostIdAndMemberId(postdto.postId(), postdto.memberId());
    }


    private Mono<Void> deleteLike(Post post, String memberId) {
        post.getPostLikes().removeIf(postLike -> postLike.getMemberId().equals(memberId));
        return save(post);
    }
    private Mono<Void> addLike(Post post, String memberId) {
        post.getPostLikes().add(new PostLike(memberId));
        return save(post);
    }
    public Mono<Void> likePost(PostWriteDto postdto) {
        return postRepository.findById(postdto.postId())
                .flatMap(post -> {
                    return isLiked(postdto.postId(), postdto.memberId())
                            .flatMap(isLiked -> {
                                if (Boolean.TRUE.equals(isLiked)) {
                                    return addLike(post, postdto.memberId());
                                } else {
                                    return deleteLike(post, postdto.memberId());
                                }
                            });
                });
    }
    public Mono<Boolean> isLiked(String postId, String memberId) {
        return postRepository.existsByPostIdAndPostLikesMemberId(postId, memberId);
    }


    public Mono<Void> addComment(PostWriteDto postdto) {
        return postRepository.findById(postdto.postId())
                .flatMap(post -> {
                    post.getComments().add(new Comment(postdto.memberId(), postdto.content()));
                    return save(post);
                });
    }
    public Mono<Void> updateComment(PostWriteDto postdto) {
        return postRepository.findById(postdto.postId())
                .flatMap(post -> {
                    post.getComments().stream()
                            .filter(comment -> comment.getMemberId().equals(postdto.memberId()) && comment.getCommentId().equals(postdto.commentId()))
                            .findFirst()
                            .ifPresent(comment -> comment.setContent(postdto.content()));
                    return save(post);
                });
    }
    public Mono<Void> deleteComment(PostWriteDto postdto) {
        return postRepository.findById(postdto.postId())
                .flatMap(post -> {
                    post.getComments().removeIf(comment -> comment.getMemberId().equals(postdto.memberId()) && comment.getCommentId().equals(postdto.commentId()));
                    return save(post);
                });
    }

    public Mono<Void> save(Post post) {
        return postRepository.save(post).then();
    }
    
}
