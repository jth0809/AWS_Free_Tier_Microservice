package com.easy.member.controller;

import org.springframework.web.bind.annotation.RestController;

import com.easy.member.dto.Memberdto;
import com.easy.member.dto.Followdto;
import com.easy.member.service.MemberService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@RestController
public class MemberController {
    MemberService memberService;

    @GetMapping("/member/{memberId}")
    public Memberdto getMember(@PathVariable long memberId) {
        return memberService.getMemberInfo(memberId);
    }
    
    @GetMapping("/member/follower/{memberId}")
    public List<Followdto> getFollower(@PathVariable long memberId) {
        return memberService.getFollowers(memberId);
    }

    @GetMapping("/member/following/{memberId}")
    public List<Followdto> getFollowing(@PathVariable long memberId) {
        return memberService.getFollowings(memberId);
    }

    @PostMapping("/member/follow")
    public void follow(@RequestBody long memberId, @RequestBody long targetId) {
        memberService.follow(memberId, targetId);
    }

    @PostMapping("/member/unfollow")
    public void unfollow(@RequestBody long memberId, @RequestBody long targetId) {
        memberService.unfollow(memberId, targetId);
    }

    @PostMapping("/member")
    public void addMember(@RequestBody String name, @RequestBody String email) {
        memberService.addMember(name, email);
    }

    @PostMapping("/member/{memberId}")
    public void updateMember(@PathVariable long memberId, @RequestBody String name, @RequestBody String email) {
        memberService.updateMember(memberId, name, email);
    }

    @PostMapping("/member/{memberId}")
    public void deleteMember(@PathVariable long memberId) {
        memberService.deleteMember(memberId);
    }

    @PostMapping("/member/{memberId}/follower/{followerId}")
    public void deleteFollower(@PathVariable long memberId, @PathVariable long followerId) {
        memberService.deleteFollower(memberId, followerId);
    } 
}
