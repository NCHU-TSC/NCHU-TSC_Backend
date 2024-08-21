package app.nchu.tsc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.nchu.tsc.codegen.types.GQL_CaseOrder;
import app.nchu.tsc.codegen.types.GQL_CaseOrderApplyMethod;
import app.nchu.tsc.codegen.types.GQL_CaseOrderReport;
import app.nchu.tsc.codegen.types.GQL_CaseOrderStatus;
import app.nchu.tsc.models.CaseOrder;
import app.nchu.tsc.models.CaseOrderReport;
import app.nchu.tsc.repositories.CaseOrderRepository;

@Service
public class CaseOrderService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private CaseOrderRepository caseOrderRepository;

    public GQL_CaseOrder toCaseOrder(CaseOrder co) {
        GQL_CaseOrder result = new GQL_CaseOrder();

        result.setId(co.getId().toString());
        result.setOrderTime(co.getOrderTime().toString());
        result.setCase(CaseService.toCase(co.getCaseID()));
        result.setMember(memberService.toMember(co.getMemberID()));
        result.setApplyMethod(GQL_CaseOrderApplyMethod.valueOf(co.getApplyMethod().name()));
        result.setBankAccount(co.getBankAccount());
        result.setComment(co.getComment());
        result.setReport(toCaseOrderReport(co.getReport()));
        result.setNote(co.getNote());

        return result;
    }

    public static GQL_CaseOrderReport toCaseOrderReport(CaseOrderReport cor) {
        GQL_CaseOrderReport result = new GQL_CaseOrderReport();

        result.setTime(cor.getReportTime().toString());
        result.setStatus(GQL_CaseOrderStatus.valueOf(cor.getReportStatus().name()));
        result.setResult(cor.getReportResult());
        result.setComment(cor.getReportComment());

        return result;
    }
    
}
