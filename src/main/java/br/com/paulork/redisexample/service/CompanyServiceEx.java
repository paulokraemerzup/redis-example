package br.com.paulork.redisexample.service;

import br.com.paulork.redisexample.model.entity.Company;
import br.com.paulork.redisexample.model.repository.CompanyRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CompanyServiceEx {

    private final CompanyRepository companyRepository;

    public CompanyServiceEx(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Cacheable(cacheNames = Company.CACHE_NAME, key = "#root.method.name")
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Cacheable(cacheNames = Company.CACHE_NAME, key = "#identifier")
    public Company findbyIdentifier(final String identifier) {
        return companyRepository.findById(identifier)
                .orElseThrow(() -> new EntityNotFoundException("Identifier not found: " + identifier));
    }

    @CacheEvict(cacheNames = Company.CACHE_NAME, key = Company.FINDALL_KEY)
    @CachePut(cacheNames = Company.CACHE_NAME, key = "#company.getIdentifier()")
    public Company create(final Company company) {
        return companyRepository.save(company);
    }

    @CacheEvict(cacheNames = Company.CACHE_NAME, key = Company.FINDALL_KEY)
    @CachePut(cacheNames = Company.CACHE_NAME, key = "#company.getIdentifier()")
    public Company update(final Company company) {
        if (company.getIdentifier() == null) {
            throw new EntityNotFoundException("Identifier is empty");
        }

        return companyRepository.save(company);
    }

    @CacheEvict(cacheNames = Company.CACHE_NAME, key = "#identifier")
    public void delete(final String identifier) {
        if (identifier == null) {
            throw new EntityNotFoundException("Identifier is empty");
        }

        companyRepository.deleteById(identifier);
    }
}