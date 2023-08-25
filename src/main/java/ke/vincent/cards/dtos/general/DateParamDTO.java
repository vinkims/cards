package ke.vincent.cards.dtos.general;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ke.vincent.cards.exceptions.InvalidInputException;
import lombok.Data;

@Data
public class DateParamDTO {
    
    Logger logger = LoggerFactory.getLogger(DateParamDTO.class);

    private LocalDateTime endDate;

    private LocalDateTime startDate;

    public DateParamDTO(Map<String, String> dateParams) throws IllegalArgumentException, 
            IllegalAccessException, NoSuchFieldException, SecurityException {

        String endDateStr = dateParams.getOrDefault("endDate", null);
        String startDateStr = dateParams.getOrDefault("startDate", null);

        if (endDateStr != null && startDateStr != null) {
            setDateField(startDateStr, this.getClass().getDeclaredField("startDate"));
            setDateField(endDateStr, this.getClass().getDeclaredField("endDate"));
            if (startDate.isAfter(endDate)) {
                throw new InvalidInputException("invalid date reange provided. startDate should be before endDate", 
                    "startDate/endDate");
            }
        } else {
            setEndDate(LocalDateTime.now());
            setStartDate(LocalDate.now().atTime(LocalTime.MIDNIGHT));
        }
    }

    private void setDateField(String dateValue, Field field) throws IllegalArgumentException, IllegalAccessException {
        try {
            Object fieldValue = dateValue == null ? LocalDateTime.now() : stringToDate(dateValue);
            field.set(this, fieldValue);
        } catch (ParseException ex) {
            field.set(this, LocalDateTime.now());
            logger.error("\n[MSG] - {}\n[CAUSE] - {}", ex.getMessage(), ex.getCause());
        } 
    }

    /**
     * Converts string to date object
     * @param dateStr
     * @return
     */
    private LocalDateTime stringToDate(String dateStr) throws ParseException {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm"));
    }
}
