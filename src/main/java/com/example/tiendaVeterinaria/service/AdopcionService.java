package com.example.tiendaVeterinaria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tiendaVeterinaria.model.Adopcion;
import com.example.tiendaVeterinaria.model.AdopcionRequest;
import com.example.tiendaVeterinaria.model.Mascota;
import com.example.tiendaVeterinaria.model.Response;
import com.example.tiendaVeterinaria.repository.AdopcionRepository;
import com.example.tiendaVeterinaria.repository.mascotaRepository;

@Service
public class AdopcionService {

	@Autowired
	private AdopcionRepository adopcionRepository;
	
	@Autowired
	private mascotaRepository mascotaRepository;
	
	public List<Adopcion> findAll() {
		return adopcionRepository.findAll();
	}

	public Adopcion findById(Integer id) {
		return adopcionRepository.findById(id).orElse(null);
	}

	public Adopcion save(Adopcion adopcion) {
	    Mascota mascota = adopcion.getMascota();
	    if (mascota != null && mascota.getId_mascota() != null) {
	        Mascota mascotaExistente = mascotaRepository.findById(mascota.getId_mascota()).orElse(null);
	        if (mascotaExistente != null) {
	            adopcion.setMascota(mascotaExistente);
	        }
	    }
	    return adopcionRepository.save(adopcion);
	}




	public void deleteById(Integer id) {
		adopcionRepository.deleteById(id);
	}
}

