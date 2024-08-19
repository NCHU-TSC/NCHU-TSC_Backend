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
public class Log {

    public enum Level {
        DEBUG, INFO, WARN, ERROR
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime time = LocalDateTime.now();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Level level = Level.INFO;

    @Column(nullable = false)
    private String namespace;

    @Column(nullable = false)
    private String detail;

    public Log(Level level, String namespace, String detail) {
        this.level = level;
        this.namespace = namespace;
        this.detail = detail;
    }

    public Log(String namespace, String detail) {
        this.namespace = namespace;
        this.detail = detail;
    }

}

