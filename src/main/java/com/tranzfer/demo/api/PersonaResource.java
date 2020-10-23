package com.tranzfer.demo.api;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/personas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonaResource {

    @Inject
    private PersonaService service;

    @GET
    public List<Persona> getAll() {
        return service.findAll();
    }

    @GET
    @Path("{id}")
    public Persona getSingle(@PathParam("id") String id) {
        return service.get(id);
    }

    @POST
    public List<Persona> add(Persona persona) {
        service.add(persona);
        return getAll();
    }
}