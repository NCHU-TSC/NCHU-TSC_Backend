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

    @Column(nullable = false)
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

    @Column
    private LocalDateTime lastPayEntryTime;

    @Builder.Default
    @Column(nullable = false)
    private boolean blocked = false;

    @Column
    private String note;

    public Member(UUID resID, String resToken, String token,
            Role role, String phone, String lineID,
            String expertise, String dutyTime, String note) {
        this.resID = resID;
        this.resToken = resToken;
        this.token = token;
        this.role = role;
        this.phone = phone;
        this.lineID = lineID;
        this.expertise = expertise;
        this.dutyTime = dutyTime;
        this.note = note;
    }
    
}
