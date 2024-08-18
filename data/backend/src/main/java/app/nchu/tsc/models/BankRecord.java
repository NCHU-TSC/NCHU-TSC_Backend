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

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime recordTime = LocalDateTime.now();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column
    private String detail;

    @Column(nullable = false)
    private Boolean direction; // true: income, false: expense

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private UUID innerAccount;

    @Column(nullable = false)
    private UUID outerAccount;

    @Column(nullable = false)
    private Boolean verified;

    @Column(nullable = false)
    private UUID operator;

    @Column
    private String note;

}
