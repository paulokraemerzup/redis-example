package br.com.paulork.redisexample.controller;

import br.com.paulork.redisexample.model.entity.Company;
import br.com.paulork.redisexample.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> findAll() {
        return companyService.findAll();
    }

    @GetMapping("/{identifier}")
    public Company findByIdentifier(@PathVariable("identifier") final String identifier) {
        return companyService.findbyIdentifier(identifier);
    }

    @PostMapping
    public Company create(@RequestBody final Company company) {
        return companyService.create(company);
    }

    @PutMapping
    public Company update(@RequestBody final Company company) {
        return companyService.update(company);
    }

    @DeleteMapping("/{identifier}")
    public void delete(@PathVariable("identifier") final String identifier) {
        companyService.delete(identifier);
    }

}