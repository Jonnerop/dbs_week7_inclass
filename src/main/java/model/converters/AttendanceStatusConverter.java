package model.converters;

import model.enums.AttendanceStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AttendanceStatusConverter implements AttributeConverter<AttendanceStatus, String> {
    @Override public String convertToDatabaseColumn(AttendanceStatus attribute){
        return attribute == null ? null : attribute.name();
    }
    @Override public AttendanceStatus convertToEntityAttribute(String dbData){
        return dbData == null ? null : AttendanceStatus.valueOf(dbData);
    }
}
