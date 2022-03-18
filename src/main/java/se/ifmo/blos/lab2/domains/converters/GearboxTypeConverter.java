package se.ifmo.blos.lab2.domains.converters;

import org.springframework.lang.Nullable;
import se.ifmo.blos.lab2.domains.enums.GearboxType;

import javax.persistence.AttributeConverter;

public class GearboxTypeConverter implements AttributeConverter<GearboxType, String> {
    @Nullable
    @Override
    public String convertToDatabaseColumn(final GearboxType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getName();
    }

    @Nullable
    @Override
    public GearboxType convertToEntityAttribute(final String dbData) {
        if (dbData == null) {
            return null;
        }

        return GearboxType.forName(dbData);
    }
}
