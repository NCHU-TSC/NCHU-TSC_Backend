package app.nchu.tsc.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import app.nchu.tsc.codegen.types.GQL_Case;
import app.nchu.tsc.codegen.types.GQL_CaseInput;
import app.nchu.tsc.codegen.types.GQL_CasePostStatus;
import app.nchu.tsc.exceptions.PermissionDeniedException;
import app.nchu.tsc.models.Case;
import app.nchu.tsc.models.CaseID;
import app.nchu.tsc.models.Member;
import app.nchu.tsc.models.Case.WithdrawalMethod;
import app.nchu.tsc.repositories.CaseRepository;
import app.nchu.tsc.services.CaseService;
import app.nchu.tsc.services.MemberService;

@DgsComponent
public class CaseController {

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private MemberService memberService;

    @DgsQuery(field = "case")
    private GQL_Case Case(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument String id) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null || !operator.getRole().isCanViewCase()) {
            throw new PermissionDeniedException("Permission Denied");
        }

        Case c = caseRepository.findById(CaseID.parse(id)).orElse(null);
        if(c == null) {
            return null;
        }

        return CaseService.toCase(c);
    }

    @DgsQuery
    private List<GQL_Case> cases(@CookieValue UUID member_id, @CookieValue String member_token) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null || !operator.getRole().isCanViewCase()) {
            throw new PermissionDeniedException("Permission Denied");
        }

        List<Case> cases = caseRepository.findAll();
        List<GQL_Case> result = new ArrayList<GQL_Case>();

        for(Case c : cases) {
            result.add(CaseService.toCase(c));
        }

        return result;
    }

    @DgsMutation
    private GQL_Case addCase(@InputArgument GQL_CaseInput data) {
        Case c = Case.builder()
                .contactName(data.getContactName())
                .email(data.getEmail())
                .phone(data.getPhone())
                .address(data.getAddress())
                .studentGender(data.getStudentGender())
                .grade(data.getGrade())
                .subjects(data.getSubjects())
                .tutorTime(data.getTutorTime())
                .tutorGenderPerference(data.getTutorGenderPerference())
                .salary(data.getSalary())
                .conditions(data.getConditions())
                .withdrawalMethod(WithdrawalMethod.valueOf(data.getWithdrawalMethod().toString()))
                .accessMethod(data.getAccessMethod())
                .problem(data.getProblem())
                .build();

        return CaseService.toCase(c);
    }

    @DgsMutation
    private GQL_Case setCasePostStatus(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument String id, @InputArgument GQL_CasePostStatus postStatus) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null || !operator.getRole().isCanModifyCase()) {
            throw new PermissionDeniedException("Permission Denied");
        }

        Case c = caseRepository.findById(CaseID.parse(id)).orElse(null);
        if(c == null) {
            return null;
        }

        c.setPostStatus(Case.PostStatue.valueOf(postStatus.toString()));
        return CaseService.toCase(caseRepository.save(c));
    }

    @DgsMutation
    private GQL_Case setCaseData(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument String id, @InputArgument GQL_CaseInput data) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null || !operator.getRole().isCanModifyCase()) {
            throw new PermissionDeniedException("Permission Denied");
        }

        Case c = caseRepository.findById(CaseID.parse(id)).orElse(null);
        if(c == null) {
            return null;
        }

        c.setContactName(data.getContactName());
        c.setEmail(data.getEmail());
        c.setPhone(data.getPhone());
        c.setAddress(data.getAddress());
        c.setStudentGender(data.getStudentGender());
        c.setGrade(data.getGrade());
        c.setSubjects(data.getSubjects());
        c.setTutorTime(data.getTutorTime());
        c.setTutorGenderPerference(data.getTutorGenderPerference());
        c.setSalary(data.getSalary());
        c.setConditions(data.getConditions());
        c.setWithdrawalMethod(WithdrawalMethod.valueOf(data.getWithdrawalMethod().toString()));
        c.setAccessMethod(data.getAccessMethod());
        c.setProblem(data.getProblem());

        return CaseService.toCase(caseRepository.save(c));
    }

    @DgsMutation
    private GQL_Case setCaseNote(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument String id, @InputArgument String note) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null || !operator.getRole().isCanModifyCase()) {
            throw new PermissionDeniedException("Permission Denied");
        }

        Case c = caseRepository.findById(CaseID.parse(id)).orElse(null);
        if(c == null) {
            return null;
        }

        c.setNote(note);
        return CaseService.toCase(caseRepository.save(c));
    }
    
}
