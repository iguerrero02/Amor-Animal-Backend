package com.example.tiendaVeterinaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tiendaVeterinaria.model.Adopcion;
import com.example.tiendaVeterinaria.model.Cita;
import com.example.tiendaVeterinaria.model.Mascota;
import com.example.tiendaVeterinaria.model.Servicio;
import com.example.tiendaVeterinaria.repository.CitaRepository;
import com.example.tiendaVeterinaria.repository.ServicioRepository;

@Service
public class CitaService {
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Autowired
    private ServicioRepository servicioRepository;
    
    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }
    
    public Cita getCitaById(Integer id) {
        return citaRepository.findById(id).orElse(null);
    }
    
    public Cita addCita(Cita cita) {
	    Servicio servicio = cita.getId_servicio();
	    if (servicio != null && servicio.getId_servicio() != null) {
	    	Servicio servicioExistente = servicioRepository.findById(servicio.getId_servicio()).orElse(null);
	        if (servicioExistente != null) {
	            cita.setId_servicio(servicioExistente);
	        }
	    }
	    return citaRepository.save(cita);
	}
    
    public void updateCita(Cita cita) {
        citaRepository.save(cita);
    }
    
    public void deleteCita(Integer id) {
        citaRepository.deleteById(id);
    }
}
