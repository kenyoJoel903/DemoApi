package com.tranzfer.demo.api.core.service;

import java.util.List;

import com.tranzfer.demo.api.core.domain.EntidadBase;

public interface IService<T extends EntidadBase> {
	
	T create(T object);
	T update(T object);
	T findById(String id);
	List<T> findAll();
	void deleteById(String id);

}
