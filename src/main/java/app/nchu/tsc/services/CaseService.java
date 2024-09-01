package app.nchu.tsc.services;

import java.time.Year;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.nchu.tsc.codegen.types.GQL_Case;
import app.nchu.tsc.codegen.types.GQL_CasePostStatus;
import app.nchu.tsc.codegen.types.GQL_CaseWithdrawalMethod;
import app.nchu.tsc.models.Case;
import app.nchu.tsc.models.CaseID;
import app.nchu.tsc.models.CaseOrder;
import app.nchu.tsc.models.CaseOrderReport;
import app.nchu.tsc.repositories.CaseOrderRepository;
import app.nchu.tsc.repositories.CaseRepository;

@Service
public class CaseService {

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private CaseOrderRepository caseOrderRepository;

    public short getNewCaseNumber(Year academicYear) {
        List<Case> cases = caseRepository.findAll();

        short result = 0;
        for(Case c : cases) {
            CaseID id = c.getId();
            if(id.getAcademicYear().equals(academicYear) && id.getCaseNumber() > result) {
                result = id.getCaseNumber();
            }
        }

        return (short)(result + 1);
    }

    public boolean isAvailable(CaseID id) {
        Case c = caseRepository.findById(id).get();
        List<CaseOrder> orders = caseOrderRepository.findByCaseID(c);

        boolean existSuccessReport = false;
        for(CaseOrder o : orders) {
            CaseOrderReport report = o.getReport();
            if(o != null && report.getReportStatus() == CaseOrderReport.Status.SUCCESS) {
                existSuccessReport = true;
                break;
            }
        }

        return c.getPostStatus() == Case.PostStatue.POSTED && !existSuccessReport;
    }

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
