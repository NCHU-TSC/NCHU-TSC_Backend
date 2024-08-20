package app.nchu.tsc.models;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "name")
@Entity
public class Role {

    public enum Name {
        SYSTEM_ACCOUNT, // 系統帳號
        PRESIDENT, // 社長
        VICE_PRESIDENT, // 副社長
        SECRETARY, // 書記
        GENERAL_AFFAIRS, // 總務
        PUBLIC_RELATIONS, // 公關
        ACTIVITY, // 活動
        DUTY_MEMBER, // 值班社員
        GENERAL_MEMBER, // 一般社員
        GUEST // 訪客
    }
    
    @Id
    private String name;

    @Column(nullable = false)
    private Boolean canViewLog;

    @Column(nullable = false)
    private Boolean canViewMember;

    @Column(nullable = false)
    private Boolean canModifyMember;

    @Column(nullable = false)
    private Boolean canViewCase;

    @Column(nullable = false)
    private Boolean canModifyCase;

    @Column(nullable = false)
    private Boolean canViewCaseOrder;

    @Column(nullable = false)
    private Boolean canModifyCaseOrder;

    @Column(nullable = false)
    private Boolean canViewBankRecord;

    @Column(nullable = false)
    private Boolean canModifyBankRecord;

    @Column(nullable = false)
    private Boolean canViewRole;

    @Column(nullable = false)
    private Boolean canModifyRole;

    @Column(nullable = false)
    private Boolean canViewSystemVariable;

    @Column(nullable = false)
    private Boolean canModifySystemVariable;
    
}
