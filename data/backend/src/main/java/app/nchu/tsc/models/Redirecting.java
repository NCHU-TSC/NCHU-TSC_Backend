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
@Entity
public class Redirecting {

    @Id
    @Column(length = 128)
    private String id;

    @Column(nullable = false)
    private String href;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime expires;

    @Column(nullable = false)
    private Boolean used;
    
}
