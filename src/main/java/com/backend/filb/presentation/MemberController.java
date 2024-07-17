package com.backend.filb.presentation;

import com.backend.filb.application.MemberService;
import com.backend.filb.domain.entity.Member;
import com.backend.filb.dto.MemberRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(
            @RequestBody MemberRequest memberRequest
    ) {
        memberService.join(memberRequest);
        return ResponseEntity.ok().body("회원가입에 성공하셨습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody MemberRequest memberRequest
    ){
        String jwt = memberService.login(memberRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        return ResponseEntity.ok().headers(headers).body("로그인에 성공하셨습니다");
    }


}
