package com.tranzfer.demo.api.core.service.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.tranzfer.demo.api.core.dao.IPersonaDao;
import com.tranzfer.demo.api.core.dao.impl.PersonaDaoImpl;
import com.tranzfer.demo.api.core.domain.Persona;
import com.tranzfer.demo.api.core.service.IPersonaService;

@ApplicationScoped
public class PersonaServiceImpl implements IPersonaService {
	
	@Inject
	private IPersonaDao dao;
	
	/*public PersonaServiceImpl() {
		dao = new PersonaDaoImpl();
	}*/

	@Override
	public Persona create(Persona object) {
		return dao.create(object);
	}

	@Override
	public Persona update(Persona object) {
		return dao.update(object);
	}

	@Override
	public Persona findById(String id) {
		return dao.findById(id);
	}

	@Override
	public List<Persona> findAll() {
		return dao.findAll();
	}

	@Override
	public void deleteById(String id) {
		dao.deleteById(id);
		
	}

}
