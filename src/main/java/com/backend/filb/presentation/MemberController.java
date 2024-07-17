package com.backend.filb.presentation;

import com.backend.filb.application.MemberService;
import com.backend.filb.domain.entity.Member;
import com.backend.filb.dto.MemberRequest;
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
    public ResponseEntity<Member> join(
            @RequestBody MemberRequest memberRequest
    ) {
        Member member = memberService.join(memberRequest);
        return ResponseEntity.ok().body(member);
    }
}
