package se.ifmo.blos.lab2.domains.converters;

import org.springframework.lang.Nullable;
import se.ifmo.blos.lab2.domains.enums.FuelType;

import javax.persistence.AttributeConverter;

public class FuelTypeConverter implements AttributeConverter<FuelType, String> {
    @Nullable
    @Override
    public String convertToDatabaseColumn(final FuelType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getName();
    }

    @Nullable
    @Override
    public FuelType convertToEntityAttribute(final String dbData) {
        if (dbData == null) {
            return null;
        }

        return FuelType.forName(dbData);
    }
}
