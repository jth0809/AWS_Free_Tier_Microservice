package com.easy.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easy.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String>{
}
