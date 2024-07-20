package com.backend.filb.domain.entity;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    public Member(Long memberId, String email, String password, String name) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public Member() {
    }

    public String getPassword() {
        return password;
    }
}
