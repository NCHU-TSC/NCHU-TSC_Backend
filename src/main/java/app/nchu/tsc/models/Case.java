package app.nchu.tsc.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity(name = "`case`")
public class Case {

    public enum PostStatue {
        PENDING,
        POSTED,
        CLOSED
    }

    public enum WithdrawalMethod {
        AUTO,
        MANUAL
    }

    @EmbeddedId
    private CaseID id;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime addTime = LocalDateTime.now();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatue postStatus;

    @Column(nullable = false)
    private String contactName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String studentGender;

    @Column(nullable = false)
    private String grade;

    @Column(nullable = false)
    private String subjects;

    @Column
    private String tutorTime;

    @Column(nullable = false)
    private String tutorGenderPerference;

    @Column
    private String salary;

    @Column
    private String conditions;

    @Column
    @Enumerated(EnumType.STRING)
    private WithdrawalMethod withdrawalMethod;

    @Column(nullable = false)
    private String accessMethod;

    @Column
    private String problem;

    @Column
    private String note;

}
