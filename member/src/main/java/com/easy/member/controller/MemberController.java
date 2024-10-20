package com.easy.member.controller;

import org.springframework.web.bind.annotation.RestController;

import com.easy.member.dto.Memberdto;
import com.easy.member.dto.Followdto;
import com.easy.member.service.MemberService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@RestController
public class MemberController {
    MemberService memberService;

    @GetMapping("/member/{memberId}")
    public Memberdto getMember(@PathVariable String memberId) {
        return memberService.getMemberInfo(memberId);
    }
    
    @GetMapping("/member/follower/{memberId}")
    public List<Followdto> getFollower(@PathVariable String memberId) {
        return memberService.getFollowers(memberId);
    }

    @GetMapping("/member/following/{memberId}")
    public List<Followdto> getFollowing(@PathVariable String memberId) {
        return memberService.getFollowings(memberId);
    }

    @PostMapping("/member/follow")
    public void follow(@RequestBody String targetId) {
        memberService.follow(targetId);
    }

    @PostMapping("/member/unfollow")
    public void unfollow(@RequestBody String targetId) {
        memberService.unfollow(targetId);
    }

    @PostMapping("/member")
    public void addMember(@RequestBody Memberdto memberdto) {
        memberService.addMember(memberdto);
    }

    @PutMapping("/member")
    public void updateMember(@RequestBody Memberdto memberdto) {
        memberService.updateMember(memberdto);
    }

    @DeleteMapping("/member")
    public void deleteMember() {
        memberService.deleteMember();
    }

    @PostMapping("/member/follower/{followerId}")
    public void deleteFollower(@PathVariable String followerId) {
        memberService.deleteFollower(followerId);
    } 
}
