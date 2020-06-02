package br.com.paulork.redisexample.model.repository;

import br.com.paulork.redisexample.model.entity.Search;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SearchRepository extends ElasticsearchRepository<Search, String> {

    Search findByIdentifier(String identifier);

    List<Search> findByType(String type);

    List<Search> findByDescription(String description);

}
