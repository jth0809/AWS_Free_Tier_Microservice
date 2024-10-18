package com.easy.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Follow {

    @EmbeddedId
    private FollowId id;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @ManyToOne
    @MapsId("followerId")
    @JoinColumn(name = "follower_id", insertable = false, updatable = false)
    private Member follower;

}

