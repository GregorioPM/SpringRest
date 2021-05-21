package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.IEquipoDao;
import com.example.demo.models.Equipo;

@RestController
@RequestMapping("/equipo")
public class EquipoController {

	@Autowired
	private IEquipoDao equipoDao;

	@GetMapping("/listar")
	public List<Equipo> listEquipo() {
		return (List<Equipo>) equipoDao.findAll();
	}

	@PostMapping("/new")
	public Equipo guardar(@RequestBody Equipo equipo) {
		return equipoDao.save(equipo);
	}

	@DeleteMapping("/delete/{id}")
	public Map<String, Object> eliminar(@PathVariable long id) {
		Map<String, Object> map = new HashMap<>();
		try {
			equipoDao.deleteById(id);
			map.put("mensaje", "Usuario Eliminado");
		} catch (DataAccessException e) {
			map.put("mensaje", "Usuario no encontrado en la BD");
			map.put("error", e.getMessage());
		}
		return map;
	}
	
	@PutMapping("/{id}")
	public Map<String, Object> equipo(@RequestBody Equipo equipo, @PathVariable long id) {
		Map<String, Object> map = new HashMap<>();
		Equipo equipoActual;
		try {
			
			equipoActual = equipoDao.findById(id).orElse(null);
			if(equipoActual == null) {
				map.put("mensaje", "Equipo no encontrado");
				return map;
			}
			equipoActual.setNombre(equipo.getNombre());
			equipoActual.setCantidadJugadores(equipo.getCantidadJugadores());
			equipoDao.save(equipoActual);
			map.put("mensaje", "Equipo Actualizado");
			map.put("equipo", equipoActual);
		} catch (DataAccessException e) {
			map.put("mensaje", "Usuario no se pudo guardar");
			map.put("error", e.getMessage());
		}
		return map;
	}
}
