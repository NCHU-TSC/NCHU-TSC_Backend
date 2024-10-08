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

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime time;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(nullable = false)
    private String namespace;

    @Column(nullable = false)
    private String detail;

    public Log(Level level, String namespace, String detail) {
        this.time = LocalDateTime.now();
        this.level = level;
        this.namespace = namespace;
        this.detail = detail;
    }

    public Log(String namespace, String detail) {
        this.time = LocalDateTime.now();
        this.level = Level.INFO;
        this.namespace = namespace;
        this.detail = detail;
    }

}

