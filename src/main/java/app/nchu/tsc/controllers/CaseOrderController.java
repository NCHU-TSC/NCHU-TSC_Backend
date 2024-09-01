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

import app.nchu.tsc.codegen.types.GQL_CaseOrder;
import app.nchu.tsc.codegen.types.GQL_CaseOrderInput;
import app.nchu.tsc.codegen.types.GQL_CaseOrderReportInput;
import app.nchu.tsc.exceptions.PermissionDeniedException;
import app.nchu.tsc.exceptions.RequestedResourceNotFound;
import app.nchu.tsc.exceptions.UnauthenticatedException;
import app.nchu.tsc.models.CaseID;
import app.nchu.tsc.models.CaseOrder;
import app.nchu.tsc.models.CaseOrderReport;
import app.nchu.tsc.models.Member;
import app.nchu.tsc.models.CaseOrder.ApplyMethod;
import app.nchu.tsc.repositories.CaseOrderRepository;
import app.nchu.tsc.repositories.CaseRepository;
import app.nchu.tsc.services.CaseOrderService;
import app.nchu.tsc.services.MemberService;

@DgsComponent
public class CaseOrderController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private CaseOrderRepository caseOrderRepository;

    @Autowired
    private CaseOrderService caseOrderService;

    @DgsQuery
    private List<GQL_CaseOrder> appliedCaseOrders(@CookieValue UUID member_id, @CookieValue String member_token) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        List<GQL_CaseOrder> result = new ArrayList<GQL_CaseOrder>();
        List<CaseOrder> caseOrders = caseOrderRepository.findByMemberID(operator);
        for(CaseOrder co : caseOrders) {
            result.add(caseOrderService.toCaseOrder(co));
        }

        return result;
    }

    @DgsQuery
    private GQL_CaseOrder caseOrder(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument UUID id) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        if(!operator.getRole().isCanViewCaseOrder()) throw new PermissionDeniedException(member_id);

        CaseOrder co = caseOrderRepository.findById(id).orElse(null);
        if(co == null) throw new RequestedResourceNotFound(CaseOrder.class.getSimpleName(), id.toString());

        return caseOrderService.toCaseOrder(co);
    }

    @DgsQuery
    private List<GQL_CaseOrder> caseOrders(@CookieValue UUID member_id, @CookieValue String member_token) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        if(!operator.getRole().isCanViewCaseOrder()) throw new PermissionDeniedException(member_id);

        List<GQL_CaseOrder> result = new ArrayList<GQL_CaseOrder>();
        List<CaseOrder> caseOrders = caseOrderRepository.findAll();
        for(CaseOrder co : caseOrders) {
            result.add(caseOrderService.toCaseOrder(co));
        }

        return result;
    }

    @DgsMutation
    private GQL_CaseOrder applyCase(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument GQL_CaseOrderInput data) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        if(!memberService.canApplyCase(member_id)) throw new PermissionDeniedException(member_id);

        CaseOrder co = new CaseOrder();
        co.setMemberID(operator);
        co.setCaseID(caseRepository.findById(CaseID.parse(data.getCase())).orElse(null));
        co.setApplyMethod(ApplyMethod.valueOf(data.getApplyMethod().name()));
        co.setBankAccount(data.getBankAccount());
        co.setComment(data.getComment());

        if(operator.getRole().isNeedPayToApplyCase()) {
            co.setApplying(true);
            co.setAccepted(false);
        } else {
            co.setApplying(false);
            co.setAccepted(true);
        }

        return caseOrderService.toCaseOrder(caseOrderRepository.save(co));
    }

    @DgsMutation
    private GQL_CaseOrder reportCaseOrder(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument UUID id, @InputArgument GQL_CaseOrderReportInput report) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        CaseOrder co = caseOrderRepository.findById(id).orElse(null);
        if(co == null) throw new RequestedResourceNotFound(CaseOrder.class.getSimpleName(), id.toString());

        co.setReport(
            CaseOrderReport.builder()
                .reportStatus(CaseOrderReport.Status.valueOf(report.getStatus().name()))
                .reportResult(report.getResult())
                .reportComment(report.getComment())
                .build()
        );

        return caseOrderService.toCaseOrder(caseOrderRepository.save(co));
    }

    @DgsMutation
    private GQL_CaseOrder determineApproveCaseOrder(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument UUID id, @InputArgument Boolean accept) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        if(!operator.getRole().isCanModifyCaseOrder()) throw new PermissionDeniedException(member_id);

        CaseOrder co = caseOrderRepository.findById(id).orElse(null);
        if(co == null) throw new RequestedResourceNotFound(CaseOrder.class.getSimpleName(), id.toString());

        co.setApplying(false);
        co.setAccepted(accept);

        return caseOrderService.toCaseOrder(caseOrderRepository.save(co));
    }

    @DgsMutation
    private GQL_CaseOrder setCaseOrderNote(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument UUID id, @InputArgument String note) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        if(!operator.getRole().isCanModifyCaseOrder()) throw new PermissionDeniedException(member_id);

        CaseOrder co = caseOrderRepository.findById(id).orElse(null);
        if(co == null) throw new RequestedResourceNotFound(CaseOrder.class.getSimpleName(), id.toString());

        co.setNote(note);

        return caseOrderService.toCaseOrder(caseOrderRepository.save(co));
    }
    
}
