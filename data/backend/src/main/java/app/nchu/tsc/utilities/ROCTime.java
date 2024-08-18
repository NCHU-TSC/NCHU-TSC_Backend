package app.nchu.tsc.utilities;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ROCTime {

    public static final long ROC_OFFSET = 1911;

    public static long toROCYear(long ceYear) {
        return ceYear - ROC_OFFSET;
    }

    public static long toCEYear(long rocYear) {
        return rocYear + ROC_OFFSET;
    }

    public static ROCTime now() {
        return new ROCTime(ZonedDateTime.now());
    }

    private ZonedDateTime ceTime;

    public ROCTime(ZonedDateTime ceTime) {
        this.setCeTime(ceTime);
    }

    public ZonedDateTime getCeTime() {
        return ceTime;
    }

    public void setCeTime(ZonedDateTime ceTime) {
        this.ceTime = ZonedDateTime.ofInstant(ceTime.toInstant(), ZoneId.of("Asia/Taipei"));
    }

    public long getYear() {
        return ceTime.getYear() - ROC_OFFSET;
    }

    public long getAcademicYear() {
        return ceTime.getMonthValue() >= 8 ? getYear() : getYear() - 1;
    }

    @Override
    public String toString() {
        return ceTime.toString();
    }
    
}
