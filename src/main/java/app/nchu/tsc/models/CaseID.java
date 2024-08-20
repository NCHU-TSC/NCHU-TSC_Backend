package app.nchu.tsc.models;

import app.nchu.tsc.utilities.ROCTime;

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
    
    @Builder.Default
    private Year academicYear = Year.now();
    private Short caseNumber;

    @Override
    public String toString() {
        // Example Result: 110-1
        return academicYear.minusYears(ROCTime.ROC_OFFSET).toString() + "-" + caseNumber;
    }

    public static CaseID parse(CharSequence caseID) {
        String[] parts = caseID.toString().split("-");
        String ceYear = ROCTime.toCEYear(Long.parseLong(parts[0])) + "";
        return new CaseID(Year.parse(ceYear), Short.parseShort(parts[1]));
    }
}
