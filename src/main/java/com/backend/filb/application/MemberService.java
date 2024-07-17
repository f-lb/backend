package com.backend.filb.application;


import com.backend.filb.domain.entity.Member;
import com.backend.filb.domain.repository.MemberRepository;
import com.backend.filb.dto.MemberRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public Member join(MemberRequest memberRequest) {
        Member member = MapMemberRequestToMember(memberRequest);
        if (!memberRepository.existsById(memberRequest.memberId())) {
            memberRepository.save(member);
        }
        throw new NoSuchElementException("이미 존재하는 회원입니다.");
    }

    private Member MapMemberRequestToMember(MemberRequest memberRequest){
        return new Member(memberRequest.memberId(),memberRequest.email(),memberRequest.password(),memberRequest.name());
    }
}
