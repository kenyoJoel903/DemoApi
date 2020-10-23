package com.tranzfer.demo.api.bussines.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.tranzfer.demo.api.core.domain.Persona;
import com.tranzfer.demo.api.core.service.IPersonaService;

@Path("/personas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonaResource {
	
	@Inject
	private IPersonaService service;
	
	@GET
	public List<Persona> getAll() {
		return service.findAll();
	}

}
