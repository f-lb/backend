package com.backend.filb.presentation;

import com.backend.filb.application.MemberService;
import com.backend.filb.dto.request.MemberLoginRequest;
import com.backend.filb.dto.request.MemberRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/join")
    public ResponseEntity<Void> join(
            @RequestBody @Valid MemberRequest memberRequest
    ) {
        memberService.join(memberRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Message", "success");
        return ResponseEntity.ok().headers(headers).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody MemberLoginRequest memberRequest
    ) {
        String jwt = memberService.login(memberRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        headers.add("Message","login success");
        return ResponseEntity.ok().headers(headers).body(null);
    }

}
