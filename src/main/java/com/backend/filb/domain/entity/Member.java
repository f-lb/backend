package com.backend.filb.domain.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberEmail;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @OneToMany
    private List<Diary> diaryList;

    public List<Diary> getDiaryList() {
        return diaryList;
    }

    public void setDiaryList(List<Diary> diaryList) {
        this.diaryList = diaryList;
    }

    public Member(Long memberEmail, String email, String password, String name, List<Diary> diaryList) {
        this.memberEmail = memberEmail;
        this.email = email;
        this.password = password;
        this.name = name;
        this.diaryList = diaryList;
    }

    public Member() {
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(memberEmail, member.memberEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberEmail);
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }
}
