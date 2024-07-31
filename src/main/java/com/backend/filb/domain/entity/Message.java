package com.backend.filb.domain.entity;

public class Message {
    private String role;
    private String content;

    // 기본 생성자
    public Message() {}

    // 매개변수 생성자
    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }
}
