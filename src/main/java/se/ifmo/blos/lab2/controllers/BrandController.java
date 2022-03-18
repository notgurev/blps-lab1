package se.ifmo.blos.lab2.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se.ifmo.blos.lab2.dtos.BrandDto;
import se.ifmo.blos.lab2.exceptions.ResourceNotFoundException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BrandController {

    private final BrandService brandService;

    @GetMapping(path = "/brands", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("permitAll()")
    public Page<BrandDto> getBrands(
            final @RequestParam(required = false) String name, final Pageable pageable) {
        if (name == null) {
            return brandService.getAllDtos(pageable);
        }
        return brandService.getAllDtosWithName(name, pageable);
    }

    @GetMapping(path = "/brands/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("permitAll()")
    public BrandDto getBrandById(final @PathVariable Long id) throws ResourceNotFoundException {
        return brandService.getDtoById(id);
    }
}
