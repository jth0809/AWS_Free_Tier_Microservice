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
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class MemberController {
    MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    Memberdto memberdto = new Memberdto( "test1", "test1@naver.com", 0L, 0L);
    Memberdto memberdto2 = new Memberdto("test2", "test2@naver.com", 0L, 0L);

    @GetMapping("/member/{memberId}")
    public Memberdto getMember(@PathVariable Long memberId) {
        return memberService.getMemberInfo(memberId);
    }
    
    @GetMapping("/member/follower/{memberId}")
    public List<Followdto> getFollower(@PathVariable Long memberId) {
        return memberService.getFollowers(memberId);
    }

    @GetMapping("/member/following/{memberId}")
    public List<Followdto> getFollowing(@PathVariable Long memberId) {
        return memberService.getFollowings(memberId);
    }

    @PostMapping("/member/follow")
    public void follow(@RequestBody Long targetId) {
        memberService.follow(targetId);
    }

    @PostMapping("/member/unfollow")
    public void unfollow(@RequestBody Long targetId) {
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
    public void deleteFollower(@PathVariable Long followerId) {
        memberService.deleteFollower(followerId);
    }
    
    @GetMapping("/member")
    public String insertmember() {
        memberService.addMember(memberdto);
        memberService.addMember(memberdto2);
        return "member added";
    }
    
}
