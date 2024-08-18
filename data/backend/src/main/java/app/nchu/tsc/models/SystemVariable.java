package app.nchu.tsc.models;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class SystemVariable {

    @Id
    @Column(length = 128, name = "`key`")
    private String key;

    @Column(nullable = false, name = "`value`")
    private String value;

    @Column(nullable = false, name = "`type`")
    private String type;

    @Column(nullable = false)
    private String defaultValue;

    @Column(nullable = false, name = "`description`")
    private String description;
    
}
