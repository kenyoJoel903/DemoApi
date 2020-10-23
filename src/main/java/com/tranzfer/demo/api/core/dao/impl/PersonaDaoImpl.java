package com.tranzfer.demo.api.core.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.tranzfer.demo.api.core.dao.IPersonaDao;
import com.tranzfer.demo.api.core.domain.Persona;
import com.tranzfer.demo.api.core.domain.PersonaAbstractService;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;

@ApplicationScoped
public class PersonaDaoImpl extends PersonaAbstractService implements IPersonaDao{
	
	@Inject
    DynamoDbClient dynamoDB;

	@Override
	public Persona create(Persona object) {
		PutItemResponse itemResponse = dynamoDB.putItem(putRequest(object));
		return Persona.from(itemResponse.attributes());
	}

	@Override
	public Persona update(Persona object) {
		UpdateItemResponse itemResponse = dynamoDB.updateItem(updateRequest(object));
		return Persona.from(itemResponse.attributes());
	}

	@Override
	public Persona findById(String id) {
		return Persona.from(dynamoDB.getItem(getRequest(id)).item());
	}

	@Override
	public List<Persona> findAll() {
		 return dynamoDB.scanPaginator(scanRequest()).items().stream()
	                .map(Persona::from)
	                .collect(Collectors.toList());
	}

	@Override
	public void deleteById(String id) {
		dynamoDB.deleteItem(deleteRequest(id));
		
	}

}
