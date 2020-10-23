package com.tranzfer.demo.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@ApplicationScoped
public class PersonaService extends AbstractService {
    
    @Inject
    DynamoDbClient dynamoDB;

    public List<Persona> findAll() {
        return dynamoDB.scanPaginator(scanRequest()).items().stream()
                .map(Persona::from)
                .collect(Collectors.toList());
    }

    public List<Persona> add(Persona fruit) {
        dynamoDB.putItem(putRequest(fruit));
        return findAll();
    }

    public Persona get(String id) {
        return Persona.from(dynamoDB.getItem(getRequest(id)).item());
    }
    
}
