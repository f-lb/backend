package com.backend.filb.presentation;

import com.backend.filb.application.MemberService;
import com.backend.filb.dto.MemberRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/join")
    public ResponseEntity join(
            @RequestBody MemberRequest memberRequest
    ) {
        memberService.join(memberRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Message", "회원가입에 성공하셨습니다.");
        return ResponseEntity.ok().headers(headers).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody MemberRequest memberRequest
    ) {
        String jwt = memberService.login(memberRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        headers.add("Message","로그인에 성공하셨습니다.");
        return ResponseEntity.ok().headers(headers).body(null);
    }

}
