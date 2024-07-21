package com.backend.filb.application;

import com.backend.filb.domain.entity.Member;
import com.backend.filb.domain.repository.MemberRepository;
import com.backend.filb.dto.MemberRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MemberService {

    private MemberRepository memberRepository;
    private JwtService jwtService;

    public MemberService(MemberRepository memberRepository, JwtService jwtService) {
        this.memberRepository = memberRepository;
        this.jwtService = jwtService;
    }

    public Member join(MemberRequest memberRequest) {
        Member member = MapMemberRequestToMember(memberRequest);
        memberRepository.findByEmail(memberRequest.email())
                .orElseThrow(() -> new NoSuchElementException("이미 존재하는 회원입니다."));
        return memberRepository.save(member);
    }

    public String login(MemberRequest memberRequest) {
        Member dbMember = memberRepository.findByEmail(memberRequest.email())
                .orElseThrow(() -> new NoSuchElementException("로그인에 실패했습니다 다시 시도해주세요"));
        if (!memberRequest.password().equals(dbMember.getPassword())) {
            throw new NoSuchElementException("로그인에 실패하였습니다. 다시 시도해주세요");
        } else {
            return jwtService.createJWT(memberRequest.email());
        }
    }

    private Member MapMemberRequestToMember(MemberRequest memberRequest) {
        return new Member(null, memberRequest.email(), memberRequest.password(), memberRequest.name());
    }
}
