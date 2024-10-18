package com.easy.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easy.member.domain.Member;
import com.easy.member.domain.Follow;
import com.easy.member.domain.FollowId;

public interface FollowRepository extends JpaRepository<Follow, FollowId>{
    Optional<Follow> findByMemberAndFollower(Member member, Member follower);
    boolean existsByFollowerAndMember(Member follower, Member member);
}
