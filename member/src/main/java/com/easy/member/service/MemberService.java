package com.easy.member.service;


import org.springframework.stereotype.Service;

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
    
    public Memberdto getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("member not found"));
        return new Memberdto(
                    member.getId(), 
                    member.getName(), 
                    member.getEmail(), 
                    (long) member.getFollowers().size(), 
                    (long) member.getFollowings().size()
                    );
    }

    public void addMember(String name, String email) {
        Member member = new Member();
        member.setName(name);
        member.setEmail(email);
        memberRepository.save(member);
    }

    public void updateMember(Long memberId, String name, String email) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("member not found"));
        if (email != null) member.setEmail(email);
        if (name != null ) member.setName(name);
        memberRepository.save(member);
    }


    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("member not found"));
        memberRepository.delete(member);
    }


    public void follow(Long memberId, Long targetId) {
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
    public void unfollow(Long memberId, Long targetId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("member not found"));
        Member target = memberRepository.findById(targetId).orElseThrow(() -> new IllegalArgumentException("target not found"));
        Follow follow = followRepository.findByMemberAndFollower(target, member).orElseThrow(() -> new IllegalArgumentException("follow not found"));
        followRepository.delete(follow);
    }
    public boolean isFollowing(Member member, Member target) {
        return followRepository.existsByFollowerAndMember(member, target);
    }

    public List<Followdto> getFollowers(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("member not found"));
        return member.getFollowers().stream().map(follow -> new Followdto(follow.getFollower().getId(), follow.getFollower().getName())).toList();
    }
    public List<Followdto> getFollowings(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("member not found"));
        return member.getFollowings().stream().map(follow -> new Followdto(follow.getMember().getId(), follow.getMember().getName())).toList();
    }

    public void deleteFollower(Long memberId, Long followerId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("member not found"));
        Member follower = memberRepository.findById(followerId).orElseThrow(() -> new IllegalArgumentException("follower not found"));
        Follow follow = followRepository.findByMemberAndFollower(member, follower).orElseThrow(() -> new IllegalArgumentException("follow not found"));
        followRepository.delete(follow);
    }

}
