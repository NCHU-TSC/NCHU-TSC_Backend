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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime joinTime = LocalDateTime.now();

    @Column(nullable = false, unique = true)
    private UUID resID;

    @Column(nullable = false)
    private String resToken;

    @Column(nullable = false)
    private String token;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Role role;

    @Column
    private String phone;

    @Column
    private String lineID;

    @Column
    private String expertise;

    @Column
    private String dutyTime;

    @Column(nullable = false)
    @Builder.Default
    private boolean applying = false;

    @Column
    private LocalDateTime lastPayEntryTime;

    @Builder.Default
    @Column(nullable = false)
    private boolean blocked = false;

    @Column
    private String note;
    
}
