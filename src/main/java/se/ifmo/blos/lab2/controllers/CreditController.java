package se.ifmo.blos.lab2.controllers;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import se.ifmo.blos.lab2.domains.User;
import se.ifmo.blos.lab2.dtos.CreditDto;
import se.ifmo.blos.lab2.exceptions.IllegalPropertyUpdateException;
import se.ifmo.blos.lab2.exceptions.ResourceAlreadyExistsException;
import se.ifmo.blos.lab2.exceptions.ResourceNotFoundException;
import se.ifmo.blos.lab2.services.CreditService;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CreditController {

  private final CreditService creditService;

  @GetMapping(path = "/credits", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER'))")
  public Page<CreditDto> getCredits(final Pageable pageable) {
    return creditService.getAllDtos(pageable);
  }

  @GetMapping(path = "/credits/{id}", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER'))")
  public CreditDto getCreditById(final @PathVariable UUID id) throws ResourceNotFoundException {
    return creditService.getDtoById(id);
  }

  @GetMapping(path = "/users/{userId}/credits", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize(
      "isAuthenticated() and (authentication.principal.id == #userId or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER'))")
  public Page<CreditDto> getCreditsByApplicant(
      final @PathVariable Long userId, final Pageable pageable) {
    return creditService.getAllDtosByApplicantId(userId, pageable);
  }

  @GetMapping(path = "/managers/{userId}/credits", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("isAuthenticated() and hasRole('ROLE_MANAGER')")
  public Page<CreditDto> getCreditsByManager(
      final @PathVariable Long userId, final Pageable pageable) {
    return creditService.getAllDtosByManagerId(userId, pageable);
  }

  @PostMapping(
      path = "users/{userId}/credits",
      consumes = APPLICATION_JSON_VALUE,
      produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("isAuthenticated() and authentication.principal.id == #userId")
  @ResponseStatus(CREATED)
  public CreditDto createCredit(
      final @PathVariable Long userId, final @RequestBody @Valid CreditDto creditDto)
      throws ResourceAlreadyExistsException {
    final var applicant =
        (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return creditService.createCreditRequest(applicant, creditDto);
  }

  @PostMapping(path = "credits/{id}/accept", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("isAuthenticated() and hasRole('ROLE_MANAGER')")
  public CreditDto updateCar(final @PathVariable UUID id)
      throws ResourceNotFoundException, IllegalPropertyUpdateException {
    final var manager =
        (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return creditService.acceptRequest(manager, id);
  }
}
