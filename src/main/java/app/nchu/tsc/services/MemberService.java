package app.nchu.tsc.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.nchu.tsc.codegen.types.GQL_Member;
import app.nchu.tsc.models.Member;
import app.nchu.tsc.repositories.MemberRepository;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

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

    public Member verifyWithToken(UUID resID, String token) {
        Member member = memberRepository.findById(resID).orElse(null);

        if(member == null || !member.getToken().equals(token)) {
            return null;
        }

        return member;
    }

    public GQL_Member toMember(Member member) {
        GQL_Member result = new GQL_Member();

        result.setId(member.getId().toString());
        result.setBasicInfo(AuthService.toUserInfo(authService.getUserInfo(member.getResID().toString(), member.getResToken())));
        result.setJoinTime(member.getJoinTime().toString());
        result.setToken(member.getToken());
        result.setRole(RoleService.toRole(member.getRole()));
        result.setPhone(member.getPhone());
        result.setLineID(member.getLineID());
        result.setExpertise(member.getExpertise());
        result.setDutyTime(member.getDutyTime());
        result.setLastPayEntryTime(member.getLastPayEntryTime().toString());
        result.setBlocked(member.isBlocked());
        result.setNote(member.getNote());

        return result;
    }

}
