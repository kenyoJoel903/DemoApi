package com.tranzfer.demo.api.core.domain;

import java.util.HashMap;
import java.util.Map;

import com.tranzfer.demo.api.core.base.AbstractService;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

public class PersonaAbstractService extends AbstractService<Persona> {
	
	public final static String COL_CORRELATIVO = "correlativo";
    public final static String COL_NOMBRES = "nombres";
    public final static String COL_EDAD = "edad";
    public final static String COL_SEXO = "sexo";
	
	public PersonaAbstractService() {
		super("PersonasCollection");
	}

	@Override
	public ScanRequest scanRequest() {
		return ScanRequest.builder().tableName(getTableName())
                .attributesToGet(COL_ID, COL_CORRELATIVO, COL_NOMBRES,
                COL_EDAD, COL_SEXO, COL_DELETE).build();
	}

	@Override
	public PutItemRequest putRequest(Persona persona) {
		Map<String, AttributeValue> item = new HashMap<>();
        item.put(COL_ID, AttributeValue.builder().s(persona.getId()).build());
        item.put(COL_CORRELATIVO, AttributeValue.builder().n(String.valueOf(persona.getCorrelativo())).build());
        item.put(COL_NOMBRES, AttributeValue.builder().s(persona.getNombres()).build());
        item.put(COL_SEXO, AttributeValue.builder().s(persona.getSexo()).build());
        item.put(COL_EDAD, AttributeValue.builder().n(persona.getEdad() + "").build());
        if(persona.getDeleted() != null)
            item.put(COL_DELETE, AttributeValue.builder().bool(true).build());
        
        return PutItemRequest.builder()
                .tableName(getTableName())
                .item(item)
                .build();
	}
	
	

}
