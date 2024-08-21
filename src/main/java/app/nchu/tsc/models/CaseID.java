package app.nchu.tsc.models;

import java.io.Serializable;
import java.time.Year;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class CaseID implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Year academicYear;
    private Short caseNumber;

    @Override
    public String toString() {
        // Example Result: 110-1
        return academicYear.toString() + "-" + caseNumber;
    }

    public static CaseID parse(CharSequence caseID) {
        String[] parts = caseID.toString().split("-");
        return new CaseID(Year.parse(parts[0]), Short.parseShort(parts[1]));
    }
    
}
