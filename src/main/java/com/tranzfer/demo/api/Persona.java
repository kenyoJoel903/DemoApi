package com.tranzfer.demo.api;

import java.util.Map;
import java.util.Objects;

import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@RegisterForReflection
public class Persona {

    private String id;
    private Long correlativo;
    private String nombres;
    private int edad;
    private String sexo;
    private Boolean deleted;



    public Persona() {
    }

    public static Persona from(Map<String, AttributeValue> item) {
        Persona persona = new Persona();
        if (item != null && !item.isEmpty()) {
            persona.setId(item.get(AbstractService.COL_ID).s());
            persona.setCorrelativo(Long.parseLong(item.get(AbstractService.COL_CORRELATIVO).n()));
            persona.setNombres(item.get(AbstractService.COL_NOMBRES).s());
            persona.setSexo(item.get(AbstractService.COL_SEXO).s());
            persona.setEdad(Integer.parseInt(item.get(AbstractService.COL_EDAD).n()));
            if(item.get(AbstractService.COL_DELETE) != null)
                persona.setDeleted(true);
        }
        return persona;
    }





    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCorrelativo() {
        return this.correlativo;
    }

    public void setCorrelativo(Long correlativo) {
        this.correlativo = correlativo;
    }

    public String getNombres() {
        return this.nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public int getEdad() {
        return this.edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Boolean isDeleted() {
        return this.deleted;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Persona id(String id) {
        this.id = id;
        return this;
    }

    public Persona correlativo(Long correlativo) {
        this.correlativo = correlativo;
        return this;
    }

    public Persona nombres(String nombres) {
        this.nombres = nombres;
        return this;
    }

    public Persona edad(int edad) {
        this.edad = edad;
        return this;
    }

    public Persona sexo(String sexo) {
        this.sexo = sexo;
        return this;
    }

    public Persona deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Persona)) {
            return false;
        }
        Persona persona = (Persona) o;
        return Objects.equals(id, persona.id) && Objects.equals(correlativo, persona.correlativo) && Objects.equals(nombres, persona.nombres) && edad == persona.edad && Objects.equals(sexo, persona.sexo) && Objects.equals(deleted, persona.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, correlativo, nombres, edad, sexo, deleted);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", correlativo='" + getCorrelativo() + "'" +
            ", nombres='" + getNombres() + "'" +
            ", edad='" + getEdad() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
  

    
}
