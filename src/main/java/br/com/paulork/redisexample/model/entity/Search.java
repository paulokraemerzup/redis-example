package br.com.paulork.redisexample.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.util.Objects;

@Document(indexName = "search")
@Getter
@Setter
@ToString
public class Search {

    @Id
    private String id;

    private String identifier;

    private String description;

    private String type;

    private String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Search search = (Search) o;
        return id.equals(search.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
