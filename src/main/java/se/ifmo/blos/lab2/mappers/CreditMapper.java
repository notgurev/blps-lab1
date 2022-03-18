package se.ifmo.blos.lab2.mappers;

import org.springframework.stereotype.Component;
import se.ifmo.blos.lab2.domains.Credit;
import se.ifmo.blos.lab2.dtos.CreditDto;
import se.ifmo.blos.lab2.exceptions.IllegalMappingOperationException;
import se.ifmo.blos.lab2.exceptions.IllegalPropertyUpdateException;

@Component("creditMapper")
public class CreditMapper implements Mapper<Credit, CreditDto> {
  @Override
  public Credit mapToPersistable(CreditDto dto) throws IllegalMappingOperationException {
    return Credit.builder().price(dto.getPrice()).build();
  }

  @Override
  public CreditDto mapToDto(Credit persistable) throws IllegalMappingOperationException {
    return CreditDto.builder()
        .id(persistable.getId())
        .price(persistable.getPrice())
        .applicantId(persistable.getApplicant() != null ? persistable.getApplicant().getId() : null)
        .applicantEmail(
            persistable.getApplicant() != null ? persistable.getApplicant().getEmail() : null)
        .managerId(persistable.getManager() != null ? persistable.getManager().getId() : null)
        .managerEmail(persistable.getManager() != null ? persistable.getManager().getEmail() : null)
        .status(persistable.getStatus())
        .build();
  }

  @Override
  public void updateFromDto(CreditDto dto, Credit persistable)
      throws IllegalMappingOperationException, IllegalPropertyUpdateException {
    if (dto.getPrice() != null) {
      persistable.setPrice(dto.getPrice());
    }
  }
}
