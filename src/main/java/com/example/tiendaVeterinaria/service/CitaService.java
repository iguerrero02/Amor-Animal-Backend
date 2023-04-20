package com.example.tiendaVeterinaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tiendaVeterinaria.model.Cita;
import com.example.tiendaVeterinaria.repository.CitaRepository;

@Service
public class CitaService {
    
    @Autowired
    private CitaRepository citaRepository;
    
    public List<Cita> getAllCitas() {
        return citaRepository.findAll();
    }
    
    public Cita getCitaById(Integer id) {
        return citaRepository.findById(id).orElse(null);
    }
    
    public void addCita(Cita cita) {
        citaRepository.save(cita);
    }
    
    public void updateCita(Cita cita) {
        citaRepository.save(cita);
    }
    
    public void deleteCita(Integer id) {
        citaRepository.deleteById(id);
    }
}
