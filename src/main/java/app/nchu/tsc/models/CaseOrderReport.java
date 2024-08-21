package app.nchu.tsc.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class CaseOrderReport implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Status {
        SUCCESS, FAILURE
    }

    @Builder.Default
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime reportTime = LocalDateTime.now();

    @Column
    @Enumerated(EnumType.STRING)
    private Status reportStatus;

    @Column
    private String reportResult;

    @Column
    private String reportComment;
    
}
