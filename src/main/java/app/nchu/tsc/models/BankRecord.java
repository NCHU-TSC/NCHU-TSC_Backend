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
public class BankRecord {
/* 
    public enum Type {
        ACTIVITY,
        JOIN,
        ORDER,
        ORDER_REFUND,
        HANDOVER,
        SPONSOR,
        FUND,
        OTHER
    }
 */
    public enum Direction {
        INCOME,
        EXPENSE,
        INTERNAL_TRANSFER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime recordTime = LocalDateTime.now();

    @Column(nullable = false)
    private String type;

    @Column
    private String detail;

    @Column(nullable = false)
    private Direction direction;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member transferIn;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member transferOut;

    @Column(nullable = false)
    private boolean verified;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member verifier;

    @Column
    private String note;

}
