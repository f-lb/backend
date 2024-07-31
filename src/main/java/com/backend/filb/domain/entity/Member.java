package com.backend.filb.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
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

    @OneToMany
    private List<Diary> diaryList;

    public Member(Long memberId, String email, String password, String name, List<Diary> diaryList) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.diaryList = diaryList;
    }

    public Member() {
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }

    public void validatePassword(String password) {
        if (password.length() < 8) {
            throw new RuntimeException("비밀번호는 8자 이상의 문자여야 합니다.");
        }
    }

    public void setDiary(Diary diary) {
        this.getDiaryList().add(diary);
    }
}
