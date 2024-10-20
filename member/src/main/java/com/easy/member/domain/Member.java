package com.easy.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    
    @Id
    @Column(name = "member_id")
    private String id;
    
    private String email;
    
    private String name;

    @OneToMany(mappedBy = "member")
    List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    List<Follow> followings = new ArrayList<>();
    

}
