package app.nchu.tsc.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.nchu.tsc.models.Member;
import app.nchu.tsc.repositories.MemberRepository;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public Member getMemberByResID(UUID resID) {
        return memberRepository.findByResID(resID);
    }

    public boolean isMemberExistsByResID(UUID resID) {
        if(memberRepository.findByResID(resID) != null) {
            return true;
        }

        return false;
    }
}
