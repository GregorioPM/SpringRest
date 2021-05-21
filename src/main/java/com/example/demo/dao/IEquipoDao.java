package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.Equipo;

public interface IEquipoDao extends CrudRepository<Equipo, Long> {
	

}
