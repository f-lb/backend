package com.backend.filb.application;

import com.backend.filb.domain.entity.Diary;
import com.backend.filb.domain.entity.Member;
import com.backend.filb.domain.repository.MemberRepository;
import com.backend.filb.dto.request.MemberLoginRequest;
import com.backend.filb.dto.request.MemberRequest;
import com.backend.filb.exception.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        Member member = mapMemberRequestToMember(memberRequest);
        memberRepository.findByEmail(memberRequest.email())
                .ifPresent(existingMember -> {
                    throw new UnauthorizedException("이미 존재하는 회원입니다.");
                });
        member.validatePassword(memberRequest.password());
        return memberRepository.save(member);
    }

    public String login(MemberLoginRequest memberRequest) {
        Member dbMember = memberRepository.findByEmail(memberRequest.email())
                .orElseThrow(() -> new UnauthorizedException("존재하지 않는 회원입니다."));
        if (!dbMember.checkPassword(memberRequest.password())) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }
        return jwtService.createJWT(memberRequest.email());
    }

    private Member mapMemberRequestToMember(MemberRequest memberRequest) {
        return new Member(null,
                memberRequest.email(),
                memberRequest.password(),
                memberRequest.name(),
                new ArrayList<Diary>());
    }

    public Member findByEmail(String jwtId) {
        return memberRepository.findByEmail(jwtId).orElseThrow(() -> new UnauthorizedException("이메일을 찾을 수 없습니다."));
    }
}
