package br.com.paulork.redisexample.commons.event;

import br.com.paulork.redisexample.model.entity.Company;
import br.com.paulork.redisexample.model.entity.Search;
import br.com.paulork.redisexample.model.repository.SearchRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class CompanyListener {

    private final SearchRepository searchRepository;

    public CompanyListener(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCustom(Company event) {
        Search search = new Search();
        search.setIdentifier(event.getIdentifier());
        search.setType("COMPANY");
        search.setDescription(event.getName());
        search.setUrl("/company");

        searchRepository.save(search);
    }
    
}
