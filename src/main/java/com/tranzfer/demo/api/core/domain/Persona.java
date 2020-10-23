package com.tranzfer.demo.api.core.domain;

import java.util.Map;

import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@RegisterForReflection
public class Persona extends EntidadBase {


    private Long correlativo;
    private String nombres;
    private int edad;
    private String sexo;




    public Persona() {
    }

    public static Persona from(Map<String, AttributeValue> item) {
        Persona persona = new Persona();
        if (item != null && !item.isEmpty()) {
            persona.setId(item.get(PersonaAbstractService.COL_ID).s());
            persona.setCorrelativo(Long.parseLong(item.get(PersonaAbstractService.COL_CORRELATIVO).n()));
            persona.setNombres(item.get(PersonaAbstractService.COL_NOMBRES).s());
            persona.setSexo(item.get(PersonaAbstractService.COL_SEXO).s());
            persona.setEdad(Integer.parseInt(item.get(PersonaAbstractService.COL_EDAD).n()));
            if(item.get(PersonaAbstractService.COL_DELETE) != null)
                persona.setDeleted(true);
        }
        return persona;
    }

	public Long getCorrelativo() {
		return correlativo;
	}

	public void setCorrelativo(Long correlativo) {
		this.correlativo = correlativo;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}



    
}
