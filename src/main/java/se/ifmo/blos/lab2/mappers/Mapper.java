package se.ifmo.blos.lab2.mappers;

import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Component;
import se.ifmo.blos.lab2.dtos.Dto;
import se.ifmo.blos.lab2.exceptions.IllegalMappingOperationException;
import se.ifmo.blos.lab2.exceptions.IllegalPropertyUpdateException;

@Component
public interface Mapper<T extends Persistable<?>, D extends Dto> {

  T mapToPersistable(final D dto) throws IllegalMappingOperationException;

  D mapToDto(final T persistable) throws IllegalMappingOperationException;

  void updateFromDto(final D dto, final T persistable)
      throws IllegalMappingOperationException, IllegalPropertyUpdateException;
}
