package com.tranzfer.demo.api.core.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tranzfer.demo.api.core.domain.EntidadBase;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

public abstract class AbstractService<T extends EntidadBase> {

	public final static String COL_ID = "id";
	public final static String COL_DELETE = "deleted";
	private String tableName;

	public AbstractService(String tableName) {
		super();
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public abstract ScanRequest scanRequest();

	public abstract PutItemRequest putRequest(T objeto);

	protected GetItemRequest getRequest(String id) {
		Map<String, AttributeValue> key = new HashMap<>();
		key.put(COL_ID, AttributeValue.builder().s(id).build());
		return GetItemRequest.builder().tableName(getTableName()).key(key).build();
	}
	
	protected UpdateItemRequest updateRequest(T objeto) {
		Field[] allFields = objeto.getClass().getFields();
		HashMap<String, Object> fields = new HashMap<>();
		for (Field field : allFields) {
			try {
				fields.put(field.getName(), field.get(objeto));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return updateRequest(objeto.getId(), fields);
	}
	
	
	
	protected UpdateItemRequest updateRequest(String id, HashMap<String,Object> fields) {
		Map<String, AttributeValue> key = new HashMap<>();
        key.put(COL_ID, AttributeValue.builder().s(id).build());
        
        return UpdateItemRequest.builder()
                .tableName(getTableName())
                .updateExpression(updateExpressionSet(fields.keySet()))
                .expressionAttributeValues(toExpressionAttrValues(fields))
                .conditionExpression("attribute_not_exists("+COL_DELETE+")")
                .key(key)
                .returnValues("ALL_NEW")
                .build();
        
	}
	
	protected DeleteItemRequest deleteRequest(String id) {
		Map<String, AttributeValue> key = new HashMap<>();
        key.put(COL_ID, AttributeValue.builder().s(id).build());
        
		return DeleteItemRequest.builder()
				.tableName(getTableName())
				.key(key)
				.build();
	}
	
	
	private String updateExpressionSet(Set<String> fields) {
        return "SET " + fields.stream()
                .map(col -> col + " = :" + cleanAttrValExp(col))
                .collect(Collectors.joining(", "));
    }
	
	
	private String cleanAttrValExp(String attrExp) {
        return attrExp.replaceAll("[^a-z|A-Z|1-9]", ""); // remove all non-alphanumeric characters
    }
	
	private Map<String, AttributeValue> toExpressionAttrValues(HashMap<String, Object> fields) {
        Map<String, AttributeValue> expAttrValues = new HashMap<>();
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            expAttrValues.put(":" + cleanAttrValExp(entry.getKey()), toAttributeValue(entry.getValue()));
        }
        return expAttrValues;
    }
	
	private AttributeValue toAttributeValue(Object obj) {
        if (obj == null)
            return AttributeValue.builder().nul(true).build();
        if (obj instanceof String)
            return AttributeValue.builder().s((String) obj).build();
        if (obj instanceof HashMap)
            return AttributeValue.builder().m(toAttributeMap(obj)).build();
        if (obj instanceof List)
            return AttributeValue.builder().l(toAttributeList((List<Object>) obj)).build();
        if (obj instanceof Number)
            return AttributeValue.builder().n(((Number) obj).toString()).build();
        if (obj instanceof Boolean)
            return AttributeValue.builder().bool((Boolean) obj).build();
        
        HashMap<String, Object> m = ObjToHashMap(obj);
        return AttributeValue.builder().m(toAttributeMap(m)).build();
    }
	
	private Map<String, AttributeValue> toAttributeMap(Object obj) {
	        if (obj==null) return null;

	        Map<String, AttributeValue> attributeMap = new HashMap<>();
	        for (Map.Entry<String, Object> entry : ObjToHashMap(obj).entrySet()) {
	            attributeMap.put(entry.getKey(), toAttributeValue(entry.getValue()));
	        }
	        return attributeMap;
	    }
	
	private HashMap<String, Object> ObjToHashMap(Object obj) {
        if (obj.getClass() == LinkedHashMap.class)
            return (HashMap<String, Object>) obj;
        
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(obj, new TypeReference<HashMap<String,Object>>(){});
    }
	
	private List<AttributeValue> toAttributeList(List<Object> objList) {
        List<AttributeValue> attributeList = new ArrayList<>();
        for (Object obj : objList) {
            attributeList.add(toAttributeValue(obj));
        }
        return attributeList;
    }
	


}
