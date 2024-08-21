package app.nchu.tsc.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.nchu.tsc.codegen.types.GQL_BankRecord;
import app.nchu.tsc.codegen.types.GQL_BankRecordDirection;
import app.nchu.tsc.codegen.types.GQL_BankRecordInput;
import app.nchu.tsc.exceptions.RequestedResourceNotFound;
import app.nchu.tsc.models.BankRecord;
import app.nchu.tsc.models.Member;
import app.nchu.tsc.repositories.BankRecordRepository;
import app.nchu.tsc.repositories.MemberRepository;

@Service
public class BankRecordService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BankRecordRepository bankRecordRepository;

    public BankRecord addBankRecord(GQL_BankRecordInput bri, Member operator) {
        BankRecord.BankRecordBuilder brbuilder = BankRecord.builder()
            .recordTime(LocalDateTime.parse(bri.getRecordTime()))
            .type(bri.getType())
            .detail(bri.getDetail())
            .direction(BankRecord.Direction.valueOf(bri.getDirection().name()))
            .amount(bri.getAmount())
            .transferIn(memberRepository.findById(UUID.fromString(bri.getTransferIn())).get())
            .transferOut(memberRepository.findById(UUID.fromString(bri.getTransferOut())).get());

        if(bri.getVerified()) {
            brbuilder.verified(true).verifier(operator);
        } else {
            brbuilder.verified(false);
        }

        return bankRecordRepository.save(brbuilder.build());
    }

    public BankRecord updateBankRecord(UUID id, GQL_BankRecordInput bri, Member operator) {
        BankRecord br = bankRecordRepository.findById(id).orElse(null);

        if(br == null) {
            throw new RequestedResourceNotFound("Bank Record", id.toString());
        }

        br.setRecordTime(LocalDateTime.parse(bri.getRecordTime()));
        br.setType(bri.getType());
        br.setDetail(bri.getDetail());
        br.setDirection(BankRecord.Direction.valueOf(bri.getDirection().name()));
        br.setAmount(bri.getAmount());
        br.setTransferIn(memberRepository.findById(UUID.fromString(bri.getTransferIn())).get());
        br.setTransferOut(memberRepository.findById(UUID.fromString(bri.getTransferOut())).get());

        if(bri.getVerified()) {
            br.setVerified(true);
            br.setVerifier(operator);
        } else {
            br.setVerified(false);
            br.setVerifier(null);
        }

        return bankRecordRepository.save(br);

    }

    public BankRecord toBankRecord(GQL_BankRecord br) {
        return BankRecord.builder()
            .id(UUID.fromString(br.getId()))
            .recordTime(LocalDateTime.parse(br.getRecordTime()))
            .type(br.getType())
            .detail(br.getDetail())
            .direction(BankRecord.Direction.valueOf(br.getDirection().name()))
            .amount(br.getAmount())
            .transferIn(memberRepository.findById(UUID.fromString(br.getTransferIn().getId())).get())
            .transferOut(memberRepository.findById(UUID.fromString(br.getTransferOut().getId())).get())
            .verified(br.getVerified())
            .verifier(memberRepository.findById(UUID.fromString(br.getVerifier().getId())).get())
            .note(br.getNote())
            .build();
    }

    public GQL_BankRecord toBankRecord(BankRecord br) {
        GQL_BankRecord result = new GQL_BankRecord();

        result.setId(br.getId().toString());
        result.setRecordTime(br.getRecordTime().toString());
        result.setType(br.getType());
        result.setDetail(br.getDetail());
        result.setDirection(GQL_BankRecordDirection.valueOf(br.getDirection().name()));
        result.setAmount(br.getAmount());
        result.setTransferIn(memberService.toMember(br.getTransferIn()));
        result.setTransferOut(memberService.toMember(br.getTransferOut()));
        result.setVerified(br.isVerified());
        result.setVerifier(memberService.toMember(br.getVerifier()));
        result.setNote(br.getNote());

        return result;
    }
    
}
