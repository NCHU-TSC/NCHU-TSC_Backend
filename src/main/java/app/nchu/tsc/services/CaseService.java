package app.nchu.tsc.services;

import org.springframework.stereotype.Service;

import app.nchu.tsc.codegen.types.GQL_Case;
import app.nchu.tsc.codegen.types.GQL_CasePostStatus;
import app.nchu.tsc.codegen.types.GQL_CaseWithdrawalMethod;
import app.nchu.tsc.models.Case;

@Service
public class CaseService {

    public static GQL_Case toCase(Case c) {
        GQL_Case result = new GQL_Case();

        result.setId(c.getId().toString());
        result.setAddTime(c.getAddTime().toString());
        result.setPostStatus(GQL_CasePostStatus.valueOf(c.getPostStatus().name()));
        result.setContactName(c.getContactName());
        result.setEmail(c.getEmail());
        result.setPhone(c.getPhone());
        result.setAddress(c.getAddress());
        result.setStudentGender(c.getStudentGender());
        result.setGrade(c.getGrade());
        result.setSubjects(c.getSubjects());
        result.setTutorTime(c.getTutorTime());
        result.setTutorGenderPerference(c.getTutorGenderPerference());
        result.setSalary(c.getSalary());
        result.setConditions(c.getConditions());
        result.setWithdrawalMethod(GQL_CaseWithdrawalMethod.valueOf(c.getWithdrawalMethod().name()));
        result.setAccessMethod(c.getAccessMethod());
        result.setProblem(c.getProblem());
        result.setNote(c.getNote());

        return result;
    }
    
}
