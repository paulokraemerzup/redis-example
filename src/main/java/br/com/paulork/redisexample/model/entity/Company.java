package br.com.paulork.redisexample.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
@ToString
@Getter
@Setter
public class Company implements Serializable {

    public static final String CACHE_NAME = "Company";
    public static final String FINDALL_KEY = "'findAll'";

    public Company() {
    }

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String identifier;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return identifier.equals(company.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

}