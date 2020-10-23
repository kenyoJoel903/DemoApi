package com.tranzfer.demo.api;

import java.util.HashMap;
import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

public class AbstractService {
    
    public final static String COL_ID = "id";
    public final static String COL_CORRELATIVO = "correlativo";
    public final static String COL_NOMBRES = "nombres";
    public final static String COL_APELLIDOS = "apellidos";
    public final static String COL_EDAD = "edad";
    public final static String COL_SEXO = "sexo";
    public final static String COL_DELETE = "deleted";

    public String getTableName() {
        return "PersonasCollection";
    }

    protected ScanRequest scanRequest() {
        return ScanRequest.builder().tableName(getTableName())
                .attributesToGet(COL_ID, COL_CORRELATIVO, COL_NOMBRES, COL_APELLIDOS,
                COL_EDAD, COL_SEXO, COL_DELETE).build();
    }

    protected PutItemRequest putRequest(Persona persona) {
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

    protected GetItemRequest getRequest(String id) {
        System.out.println("IIIID: " + id);
        Map<String, AttributeValue> key = new HashMap<>();
        key.put(COL_ID, AttributeValue.builder().s(id).build());

        return GetItemRequest.builder()
                .tableName(getTableName())
                .key(key)
                .build();
    }

}
