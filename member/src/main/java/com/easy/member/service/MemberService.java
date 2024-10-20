package com.easy.member.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easy.member.domain.Member;
import com.easy.member.domain.Follow;
import com.easy.member.domain.FollowId;
import com.easy.member.dto.Followdto;
import com.easy.member.dto.Memberdto;
import com.easy.member.repository.FollowRepository;
import com.easy.member.repository.MemberRepository;

import java.util.List;

@Service
public class MemberService {
    MemberRepository memberRepository;
    FollowRepository followRepository;
    
    public MemberService(MemberRepository memberRepository, FollowRepository followRepository) {
        this.memberRepository = memberRepository;
        this.followRepository = followRepository;
    }

    private String getMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        if (jwt.getClaim("Id") == null) {
            throw new IllegalArgumentException("unauthorized or invalid token");
        }
        return jwt.getClaim("Id").toString();
    }
    
    public Memberdto getMemberInfo(String memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("member not found"));
        return new Memberdto(
                    member.getId(), 
                    member.getName(), 
                    member.getEmail(), 
                    (long) member.getFollowers().size(), 
                    (long) member.getFollowings().size()
                    );
    }
    @Transactional
    public void addMember(Memberdto memberdto) {
        Member member = new Member();
        member.setName(memberdto.name());
        member.setEmail(memberdto.email());
        memberRepository.save(member);
    }

    @Transactional
    public void updateMember(Memberdto memberdto) {
        String memberId = getMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("member not found"));
        if (memberdto.email() != null) member.setEmail(memberdto.email());
        if (memberdto.name() != null ) member.setName(memberdto.name());
        memberRepository.save(member);
    }

    @Transactional
    public void deleteMember() {
        String memberId = getMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("member not found"));
        memberRepository.delete(member);
    }

    @Transactional
    public void follow(String targetId) {
        String memberId = getMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("member not found"));
        Member target = memberRepository.findById(targetId).orElseThrow(() -> new IllegalArgumentException("target not found"));
        if (isFollowing(member, target)) {
            throw new IllegalArgumentException("already following");
        }
        
        Follow follow = new Follow();
        FollowId followId = new FollowId(target.getId(), member.getId());
        
        follow.setId(followId);
        follow.setMember(target);
        follow.setFollower(member);
        
        followRepository.save(follow);
    }

    @Transactional
    public void unfollow(String targetId) {
        String memberId = getMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("member not found"));
        Member target = memberRepository.findById(targetId).orElseThrow(() -> new IllegalArgumentException("target not found"));
        Follow follow = followRepository.findByMemberAndFollower(target, member).orElseThrow(() -> new IllegalArgumentException("follow not found"));
        followRepository.delete(follow);
    }
    private boolean isFollowing(Member member, Member target) {
        return followRepository.existsByFollowerAndMember(member, target);
    }

    public List<Followdto> getFollowers(String memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("member not found"));
        return member.getFollowers().stream().map(follow -> new Followdto(follow.getFollower().getId(), follow.getFollower().getName())).toList();
    }
    public List<Followdto> getFollowings(String memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("member not found"));
        return member.getFollowings().stream().map(follow -> new Followdto(follow.getMember().getId(), follow.getMember().getName())).toList();
    }

    
    @Transactional
    public void deleteFollower(String followerId) {
        String memberId = getMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("member not found"));
        Member follower = memberRepository.findById(followerId).orElseThrow(() -> new IllegalArgumentException("follower not found"));
        Follow follow = followRepository.findByMemberAndFollower(member, follower).orElseThrow(() -> new IllegalArgumentException("follow not found"));
        followRepository.delete(follow);
    }

}
