package se.ifmo.blos.lab2.services;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ifmo.blos.lab2.domains.User;
import se.ifmo.blos.lab2.dtos.UserDto;
import se.ifmo.blos.lab2.exceptions.IllegalPropertyUpdateException;
import se.ifmo.blos.lab2.exceptions.ResourceAlreadyExistsException;
import se.ifmo.blos.lab2.exceptions.ResourceNotFoundException;
import se.ifmo.blos.lab2.mappers.UserMapper;
import se.ifmo.blos.lab2.repositories.UserRepository;

import java.util.UUID;

import static java.lang.String.format;

@Service("userService")
@Transactional(
        rollbackFor = {ResourceNotFoundException.class, ResourceAlreadyExistsException.class})
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    protected UserService(final UserRepository userRepository, final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDto createFromDto(final UserDto dto) throws ResourceAlreadyExistsException {
        if (isAlreadyExists(dto)) {
            throw new ResourceAlreadyExistsException(
                    format("USer with email %s already exists.", dto.getEmail()));
        }
        final var toPersist = userMapper.mapToPersistable(dto);
        final var persisted = userRepository.save(toPersist);
        return userMapper.mapToDto(persisted);
    }


    @Transactional(readOnly = true)
    public Page<User> getAllEntities(final Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    @Transactional(readOnly = true)
    public Page<User> getAllEntities(
            final Specification<User> specification, final Pageable pageable) {
        return userRepository.findAll(specification, pageable);
    }


    @Transactional(readOnly = true)
    public Page<UserDto> getAllDtos(final Pageable pageable) {
        return getAllEntities(pageable).map(userMapper::mapToDto);
    }


    @Transactional(readOnly = true)
    public Page<UserDto> getAllDtos(
            final Specification<User> specification, final Pageable pageable) {
        return getAllEntities(specification, pageable).map(userMapper::mapToDto);
    }


    @Transactional(readOnly = true)
    public User getEntityById(final Long id) throws ResourceNotFoundException {
        return userRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(format("User with id %s was not found.", id)));
    }


    @Transactional(readOnly = true)
    public UserDto getDtoById(final Long id) throws ResourceNotFoundException {
        return userMapper.mapToDto(getEntityById(id));
    }


    @Transactional
    public UserDto updateFromDto(final UserDto dto, final Long id)
            throws ResourceNotFoundException, IllegalPropertyUpdateException {
        final var persistable = getEntityById(id);
        userMapper.updateFromDto(dto, persistable);
        return userMapper.mapToDto(persistable);
    }


    @Transactional
    public void removeById(final Long id) throws ResourceNotFoundException {
        final var persistable = getEntityById(id);
        userRepository.delete(persistable);
    }


    @Transactional(readOnly = true)
    public boolean isAlreadyExists(UserDto dto) {
        return userRepository.existsByEmail(dto.getEmail());
    }

    @Transactional(readOnly = true)
    public UserDto getByOwnedCarId(final UUID carId) {
        var user =
                userRepository.findByCarId(carId).orElseThrow(() -> new UsernameNotFoundException(
                        format("Owner for car with id %s was not found.", carId))
                );
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(
                format("Username with email %s was not found.", username))
        );
    }
}
