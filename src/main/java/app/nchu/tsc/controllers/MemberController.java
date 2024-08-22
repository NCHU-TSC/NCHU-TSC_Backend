package app.nchu.tsc.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import app.nchu.tsc.codegen.types.GQL_Member;
import app.nchu.tsc.codegen.types.GQL_MemberInput;
import app.nchu.tsc.exceptions.PermissionDeniedException;
import app.nchu.tsc.exceptions.RequestedResourceNotFound;
import app.nchu.tsc.exceptions.UnauthenticatedException;
import app.nchu.tsc.models.Member;
import app.nchu.tsc.repositories.MemberRepository;
import app.nchu.tsc.repositories.RoleRepository;
import app.nchu.tsc.services.MemberService;

@DgsComponent
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @DgsQuery
    private GQL_Member currentMember(@CookieValue UUID member_id, @CookieValue String member_token) {
        return memberService.toMember(memberService.verifyWithToken(member_id, member_token));
    }

    @DgsQuery
    private GQL_Member member(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument UUID id) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        if(!operator.getRole().isCanViewMember()) throw new PermissionDeniedException(member_id);

        Member member = memberRepository.findById(id).orElse(null);

        if(member == null) throw new RequestedResourceNotFound("Member", id.toString());

        return memberService.toMember(member);
    }

    @DgsQuery
    private List<GQL_Member> members(@CookieValue UUID member_id, @CookieValue String member_token) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        if(!operator.getRole().isCanViewMember()) throw new PermissionDeniedException(member_id);

        List<Member> members = memberRepository.findAll();
        List<GQL_Member> result = new ArrayList<GQL_Member>();

        for(Member member : members) {
            result.add(memberService.toMember(member));
        }

        return result;
    }

    @DgsMutation
    private GQL_Member applyGeneralMember(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument GQL_MemberInput data) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        operator.setApplying(true);
        operator.setPhone(data.getPhone());
        operator.setLineID(data.getLineID());
        operator.setExpertise(data.getExpertise());
        operator.setDutyTime(data.getDutyTime());

        return memberService.toMember(memberRepository.save(operator));
    }

    @DgsMutation
    private GQL_Member setCurrentMemberData(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument GQL_MemberInput data) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        operator.setPhone(data.getPhone());
        operator.setLineID(data.getLineID());
        operator.setExpertise(data.getExpertise());
        operator.setDutyTime(data.getDutyTime());

        return memberService.toMember(memberRepository.save(operator)) ;
    }

    @DgsMutation
    private GQL_Member determineJoinMember(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument UUID id, @InputArgument boolean accept) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        if(!operator.getRole().isCanModifyMember()) throw new PermissionDeniedException(member_id);

        Member member = memberRepository.findById(id).orElse(null);
        if(member == null) throw new RequestedResourceNotFound("Member", id.toString());

        member.setApplying(false);
        if(accept) {
            member.setLastPayEntryTime(LocalDateTime.now());
        }

        return memberService.toMember(memberRepository.save(member));
    }

    @DgsMutation
    private GQL_Member setMemberData(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument UUID id, @InputArgument GQL_MemberInput data) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        if(!operator.getRole().isCanModifyMember()) throw new PermissionDeniedException(member_id);

        Member member = memberRepository.findById(id).orElse(null);
        if(member == null) throw new RequestedResourceNotFound("Member", id.toString());

        member.setPhone(data.getPhone());
        member.setLineID(data.getLineID());
        member.setExpertise(data.getExpertise());
        member.setDutyTime(data.getDutyTime());

        return memberService.toMember(memberRepository.save(member));
    }

    @DgsMutation
    private GQL_Member setMemberRole(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument UUID id, @InputArgument String roleName) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        if(!operator.getRole().isCanModifyMember()) throw new PermissionDeniedException(member_id);

        Member member = memberRepository.findById(id).orElse(null);
        if(member == null) throw new RequestedResourceNotFound("Member", id.toString());

        member.setRole(roleRepository.findById(roleName).orElse(null));

        return memberService.toMember(memberRepository.save(member));
    }

    @DgsMutation
    private GQL_Member setMemberNote(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument UUID id, @InputArgument String note) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        if(!operator.getRole().isCanModifyMember()) throw new PermissionDeniedException(member_id);

        Member member = memberRepository.findById(id).orElse(null);
        if(member == null) throw new RequestedResourceNotFound("Member", id.toString());

        member.setNote(note);

        return memberService.toMember(memberRepository.save(member));
    }

    @DgsMutation
    private GQL_Member setMemberBlocked(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument UUID id, @InputArgument boolean blocked) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        if(!operator.getRole().isCanModifyMember()) throw new PermissionDeniedException(member_id);

        Member member = memberRepository.findById(id).orElse(null);
        if(member == null) throw new RequestedResourceNotFound("Member", id.toString());

        member.setBlocked(blocked);

        return memberService.toMember(memberRepository.save(member));
    }

}
