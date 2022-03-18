package se.ifmo.blos.lab2.services;

import static java.lang.String.format;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;
import static se.ifmo.blos.lab2.domains.CreditStatus.ACCEPTED;
import static se.ifmo.blos.lab2.domains.CreditStatus.CREATED;
import static se.ifmo.blos.lab2.domains.CreditStatus.REJECTED;
import static se.ifmo.blos.lab2.domains.CreditStatus.REVIEW;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import se.ifmo.blos.lab2.domains.Credit;
import se.ifmo.blos.lab2.domains.User;
import se.ifmo.blos.lab2.dtos.CreditDto;
import se.ifmo.blos.lab2.exceptions.IllegalPropertyUpdateException;
import se.ifmo.blos.lab2.exceptions.ResourceAlreadyExistsException;
import se.ifmo.blos.lab2.exceptions.ResourceNotFoundException;
import se.ifmo.blos.lab2.mappers.CreditMapper;
import se.ifmo.blos.lab2.repositories.CreditRepository;

@Service("creditService")
@Transactional(
    rollbackFor = {ResourceNotFoundException.class, ResourceAlreadyExistsException.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CreditService implements CommonService<Credit, UUID, CreditDto> {

  private final CreditRepository creditRepository;
  private final CreditMapper creditMapper;
  private final RestTemplate restTemplate;

  @Override
  @Transactional
  public CreditDto createFromDto(final CreditDto dto) throws ResourceAlreadyExistsException {
    /*if (isAlreadyExists(dto)) {
      throw new ResourceAlreadyExistsException(
              format("Credit with id %s already exists.", dto.getApplicantId()));
    }*/
    final var toPersist = creditMapper.mapToPersistable(dto);
    final var persisted = creditRepository.save(toPersist);
    return creditMapper.mapToDto(persisted);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Credit> getAllEntities(final Pageable pageable) {
    return creditRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Credit> getAllEntities(
      final Specification<Credit> specification, final Pageable pageable) {
    return creditRepository.findAll(specification, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<CreditDto> getAllDtos(final Pageable pageable) {
    return getAllEntities(pageable).map(creditMapper::mapToDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<CreditDto> getAllDtos(
      final Specification<Credit> specification, final Pageable pageable) {
    return getAllEntities(specification, pageable).map(creditMapper::mapToDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Credit getEntityById(final UUID id) throws ResourceNotFoundException {
    return creditRepository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException(format("Credit with id %s was not found.", id)));
  }

  @Override
  @Transactional(readOnly = true)
  public CreditDto getDtoById(final UUID id) throws ResourceNotFoundException {
    return creditMapper.mapToDto(getEntityById(id));
  }

  @Override
  @Transactional
  public CreditDto updateFromDto(final CreditDto dto, final UUID id)
      throws ResourceNotFoundException, IllegalPropertyUpdateException {
    final var persistable = getEntityById(id);
    creditMapper.updateFromDto(dto, persistable);
    return creditMapper.mapToDto(persistable);
  }

  @Override
  @Transactional
  public void removeById(final UUID id) throws ResourceNotFoundException {
    final var persistable = getEntityById(id);
    creditRepository.delete(persistable);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean isAlreadyExists(final CreditDto dto) {
    if (dto.getId() == null) {
      return false;
    }
    return creditRepository.existsById(dto.getId());
  }

  @Transactional(readOnly = true)
  public Page<Credit> getAllEntitiesByApplicantId(final Long id, final Pageable pageable) {
    return creditRepository.findAllByApplicantId(id, pageable);
  }

  @Transactional(readOnly = true)
  public Page<Credit> getAllEntitiesByManagerId(final Long id, final Pageable pageable) {
    return creditRepository.findAllByManagerId(id, pageable);
  }

  @Transactional(readOnly = true)
  public Page<CreditDto> getAllDtosByApplicantId(final Long id, final Pageable pageable) {
    return getAllEntitiesByApplicantId(id, pageable).map(creditMapper::mapToDto);
  }

  @Transactional(readOnly = true)
  public Page<CreditDto> getAllDtosByManagerId(final Long id, final Pageable pageable) {
    return getAllEntitiesByManagerId(id, pageable).map(creditMapper::mapToDto);
  }

  @Transactional
  public CreditDto createCreditRequest(final User applicant, final CreditDto creditDto) {
    final var credit = creditMapper.mapToPersistable(creditDto);
    credit.setApplicant(applicant);
    credit.setStatus(CREATED);
    return creditMapper.mapToDto(creditRepository.save(credit));
  }

  @Transactional
  public CreditDto acceptRequest(final User manager, UUID id) throws ResourceNotFoundException {
    final var credit = getEntityById(id);
    credit.setManager(manager);
    credit.setStatus(REVIEW);

    final HttpEntity<Object> entity = new HttpEntity<>(new HttpHeaders());
    final var response = restTemplate.exchange("/api/v1/requests", POST, entity, Object.class);
    if (response.getStatusCode() == OK) {
      credit.setStatus(ACCEPTED);
    } else {
      credit.setStatus(REJECTED);
    }
    return creditMapper.mapToDto(credit);
  }
}
