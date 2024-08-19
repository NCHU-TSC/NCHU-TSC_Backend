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

    @Column(nullable = false)
    private UUID caseID;

    @Column(nullable = false)
    private UUID memberID;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplyMethod applyMethod;

    @Column
    private String bankAccount;

    @Column
    private String comment;

    @Embedded
    private CaseOrderReport report;

    @Column
    private String note;
    
}
