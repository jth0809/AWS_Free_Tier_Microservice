package com.easy.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.easy.post.domain.Comment;
import com.easy.post.domain.Content;
import com.easy.post.domain.Post;
import com.easy.post.domain.PostLike;
import com.easy.post.dto.AndroidPost;
import com.easy.post.dto.PostWriteDto;
import com.easy.post.dto.Postdto;
import com.easy.post.dto.Postsdto;
import com.easy.post.repository.ReactivePostRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactivePostService {

    private final ReactivePostRepository postRepository;


    public ReactivePostService(ReactivePostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Mono<String> getMemberId() {
        return ReactiveSecurityContextHolder.getContext()
            .map(securityContext -> {
                Authentication authentication = securityContext.getAuthentication();
                if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
                    throw new IllegalStateException("Authentication not found or is not a JWT");
                }
                Jwt jwt = (Jwt) authentication.getPrincipal();
                return jwt.getClaim("username").toString();
            })
            .doOnNext(username -> System.out.println("Extracted username: " + username)); // 디버깅 로그
    }

    public Mono<List<Postsdto>> getPosts() {
        return postRepository.findAll()
                .map(post -> new Postsdto(
                        post.getPostId(),
                        post.getMemberId(),
                        post.getTitle(),
                        post.getContent(),
                        (long) post.getPostLikes().size()
                ))
                .collect(Collectors.toList());
    }
    public Mono<Postdto> getPost(String postId) {
        return postRepository.findById(postId)
                .map(p -> new Postdto(
                        p.getPostId(),
                        p.getMemberId(),
                        p.getTitle(),
                        p.getContent(),
                        (long) p.getPostLikes().size(),
                        false,
                        p.getComments()
                ));
    }

    public Mono<Post> addPost(PostWriteDto postWriteDto) {
        return getMemberId().flatMap(memberId -> {
            Post post = new Post();
            post.setMemberId(memberId);
            post.setTitle(postWriteDto.title());
            post.setContent(new Content(postWriteDto.content(), postWriteDto.date()));
            
            return postRepository.save(post)
                    .flatMap(savedPost -> postRepository.findById(savedPost.getPostId()));
        });
    }

    public Mono<Void> updatePost(PostWriteDto postdto) {
        return postRepository.findById(postdto.postId())
                .flatMap(post -> {
                    if (!post.getMemberId().equals(postdto.memberId())) {
                        return Mono.error(new IllegalArgumentException("Member does not have permission to update this post"));
                    }
                    post.setTitle(postdto.title());
                    post.setContent(new Content(postdto.content(), postdto.date()));
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
    public Mono<Void> likePost(Mono<PostWriteDto> postdto) {
        return postdto
                .flatMap(postWriteDto -> postRepository.findById(postWriteDto.postId())
                .flatMap(post -> {
                    return isLiked(postWriteDto.postId(), postWriteDto.memberId())
                            .flatMap(isLiked -> {
                                if (Boolean.FALSE.equals(isLiked)) {
                                    return addLike(post, postWriteDto.memberId());
                                } else {
                                    return deleteLike(post, postWriteDto.memberId());
                                }
                            });
                }));
    }
    public Mono<Boolean> isLiked(String postId, String memberId) {
        return postRepository.existsByPostIdAndPostLikesMemberId(postId, memberId);
    }


    public Mono<Void> addComment(PostWriteDto postdto) {
        return postRepository.findById(postdto.postId())
                .flatMap(post -> {
                    post.getComments().add(new Comment(null, postdto.memberId(), postdto.content(), postdto.date()));
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
