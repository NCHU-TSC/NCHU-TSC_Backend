package app.nchu.tsc.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class CaseOrder {

    public enum ApplyMethod {
        ONLINE, OFFLINE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime orderTime = LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
        @JoinColumn(name = "academic_year", referencedColumnName = "academic_year"),
        @JoinColumn(name = "case_number", referencedColumnName = "case_number")
    })
    private Case caseID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Member memberID;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplyMethod applyMethod;

    @Column
    private String bankAccount;

    @Column
    private String comment;

    @Embedded
    private CaseOrderReport report;

    @Column(nullable = false)
    private boolean applying;

    @Column(nullable = false)
    private boolean accepted;

    @Column
    private String note;
    
}
