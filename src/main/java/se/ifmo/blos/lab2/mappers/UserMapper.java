package se.ifmo.blos.lab2.mappers;

import org.springframework.stereotype.Component;
import se.ifmo.blos.lab2.domains.User;
import se.ifmo.blos.lab2.dtos.UserDto;
import se.ifmo.blos.lab2.exceptions.IllegalMappingOperationException;

@Component("userMapper")
public class UserMapper implements Mapper<User, UserDto> {

  @Override
  public User mapToPersistable(final UserDto dto) throws IllegalMappingOperationException {
    throw new IllegalMappingOperationException("Unsupported mapping of UserDto to User.");
  }

  @Override
  public UserDto mapToDto(final User persistable) throws IllegalMappingOperationException {
    return new UserDto(persistable.getEmail(), persistable.getPhoneNumber(), persistable.getRole());
  }

  @Override
  public void updateFromDto(final UserDto dto, final User persistable)
      throws IllegalMappingOperationException {
    throw new IllegalMappingOperationException("Unsupported mapping of UserDto to User.");
  }
}
