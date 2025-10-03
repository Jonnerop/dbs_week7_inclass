package model.converters;

import model.enums.Rank;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RankConverter implements AttributeConverter<Rank, Integer> {
    @Override public Integer convertToDatabaseColumn(Rank attribute){
        return attribute == null ? null : attribute.code;
    }
    @Override public Rank convertToEntityAttribute(Integer dbData){
        return Rank.fromCode(dbData);
    }
}

