package se.ifmo.blos.lab2.domains.converters;

import javax.persistence.AttributeConverter;
import org.springframework.lang.Nullable;
import se.ifmo.blos.lab2.domains.enums.DriveUnitType;

public class DriveUnitTypeConverter implements AttributeConverter<DriveUnitType, String> {

  @Nullable
  @Override
  public String convertToDatabaseColumn(final DriveUnitType attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.getName();
  }

  @Nullable
  @Override
  public DriveUnitType convertToEntityAttribute(final String dbData) {
    if (dbData == null) {
      return null;
    }

    return DriveUnitType.forName(dbData);
  }
}
