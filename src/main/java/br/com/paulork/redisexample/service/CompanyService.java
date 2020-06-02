package br.com.paulork.redisexample.service;

import br.com.paulork.redisexample.model.entity.Company;
import br.com.paulork.redisexample.model.entity.Search;
import br.com.paulork.redisexample.model.repository.CompanyRepository;
import br.com.paulork.redisexample.model.repository.SearchRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final SearchRepository searchRepository;
    private final ApplicationEventPublisher publisher;

    public CompanyService(CompanyRepository companyRepository, SearchRepository searchRepository, ApplicationEventPublisher publisher) {
        this.companyRepository = companyRepository;
        this.searchRepository = searchRepository;
        this.publisher = publisher;
    }

    @Cacheable(cacheNames = Company.CACHE_NAME, key = Company.FINDALL_KEY)
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Cacheable(cacheNames = Company.CACHE_NAME, key = "#identifier")
    public Company findbyIdentifier(final String identifier) {

        findInES(identifier);

        return companyRepository.findById(identifier)
                .orElseThrow(() -> new EntityNotFoundException("Identifier not found: " + identifier));
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = Company.CACHE_NAME, key = Company.FINDALL_KEY)
    }, put = {
            @CachePut(cacheNames = Company.CACHE_NAME, key = "#company.getIdentifier()")
    })
    @Transactional
    public Company create(final Company company) {
        Company save = companyRepository.save(company);
        publisher.publishEvent(save);
        return save;
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = Company.CACHE_NAME, key = Company.FINDALL_KEY)
    }, put = {
            @CachePut(cacheNames = Company.CACHE_NAME, key = "#company.getIdentifier()")
    })
    public Company update(final Company company) {
        if (company.getIdentifier() == null) {
            throw new EntityNotFoundException("Identifier is empty");
        }

        return companyRepository.save(company);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = Company.CACHE_NAME, key = Company.FINDALL_KEY),
            @CacheEvict(cacheNames = Company.CACHE_NAME, key = "#identifier")
    })
    public void delete(final String identifier) {
        if (identifier == null) {
            throw new EntityNotFoundException("Identifier is empty");
        }

        companyRepository.deleteById(identifier);
    }


    private void findInES(String identifier) {
        long ini = System.currentTimeMillis();
        Search search = searchRepository.findByIdentifier(identifier);
        long fim = System.currentTimeMillis() - ini;
        System.out.println("=================================================");
        System.out.println("------> FIND TIME: "+fim+" ms");
        System.out.println("------> OBJECT: "+search);
        System.out.println("=================================================");
    }

}