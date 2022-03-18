package se.ifmo.blos.lab2.requests;

import java.util.UUID;
import lombok.Data;
import se.ifmo.blos.lab2.domains.enums.DriveUnitType;
import se.ifmo.blos.lab2.domains.enums.FuelType;
import se.ifmo.blos.lab2.domains.enums.GearboxType;

@Data
public class CarRequestParameters {
  private UUID id;
  private String vin;
  private Long brandId;
  private String modelName;
  private Integer year;
  private String city;
  private Double price;
  private Double mileage;
  private Integer generation;
  private DriveUnitType driveUnitType;
  private FuelType fuelType;
  private GearboxType gearboxType;
  private String color;
  private String additionalInfo;
  private Boolean isSold;
}
