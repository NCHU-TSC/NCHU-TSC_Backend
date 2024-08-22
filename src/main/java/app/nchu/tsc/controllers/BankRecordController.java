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

import app.nchu.tsc.codegen.types.GQL_BankRecord;
import app.nchu.tsc.codegen.types.GQL_BankRecordInput;
import app.nchu.tsc.exceptions.*;
import app.nchu.tsc.models.BankRecord;
import app.nchu.tsc.models.Member;
import app.nchu.tsc.repositories.BankRecordRepository;
import app.nchu.tsc.services.BankRecordService;
import app.nchu.tsc.services.MemberService;

@DgsComponent
public class BankRecordController {

    @Autowired
    private BankRecordRepository bankRecordRepository;

    @Autowired
    private BankRecordService bankRecordService;

    @Autowired
    private MemberService memberService;

    @DgsQuery
    private GQL_BankRecord bankRecord(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument UUID id) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) {
            throw new UnauthenticatedException();
        }

        if(!operator.getRole().isCanViewBankRecord()) {
            throw new PermissionDeniedException(member_id);
        }

        BankRecord bankRecord = bankRecordRepository.findById(id).orElse(null);

        if(bankRecord == null) {
            throw new RequestedResourceNotFound("Bank Record", id.toString());
        }

        return bankRecordService.toBankRecord(bankRecord);
    }

    @DgsQuery
    private List<GQL_BankRecord> bankRecords(@CookieValue UUID member_id, @CookieValue String member_token) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) {
            throw new UnauthenticatedException();
        }

        if(!operator.getRole().isCanViewBankRecord()) {
            throw new PermissionDeniedException(member_id);
        }

        List<GQL_BankRecord> result = new ArrayList<GQL_BankRecord>();

        for(BankRecord br : bankRecordRepository.findAll()) {
            result.add(bankRecordService.toBankRecord(br));
        }

        return result;
    }

    @DgsMutation
    private GQL_BankRecord addBankRecord(@CookieValue UUID member_id, @CookieValue String member_token, GQL_BankRecordInput data) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) {
            throw new UnauthenticatedException();
        }

        if(!operator.getRole().isCanModifyBankRecord()) {
            throw new PermissionDeniedException(member_id);
        }

        return bankRecordService.toBankRecord(bankRecordService.addBankRecord(data, operator));
    }

    @DgsMutation
    private GQL_BankRecord setBankRecordData(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument UUID id, @InputArgument GQL_BankRecordInput data) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) {
            throw new UnauthenticatedException();
        }

        if(!operator.getRole().isCanModifyBankRecord()) {
            throw new PermissionDeniedException(member_id);
        }

        return bankRecordService.toBankRecord(bankRecordService.updateBankRecord(id, data, operator));
    }

    @DgsMutation
    private boolean deleteBankRecord(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument UUID id) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) {
            throw new UnauthenticatedException();
        }

        if(!operator.getRole().isCanModifyBankRecord()) {
            throw new PermissionDeniedException(member_id);
        }

        bankRecordRepository.deleteById(id);
        return true;
    }
    
}
