package com.example.tiendaVeterinaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.tiendaVeterinaria.repository.mascotaRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.tiendaVeterinaria.model.Mascota;

@Service
public class MascotaService {
	 @Autowired
	    private  mascotaRepository mascotaRepository;
	    
	    // Obtener todas las mascotas
	    public List<Mascota> getAllMascotas() {
	        return mascotaRepository.findAll();
	    }
	    
	    // Obtener una mascota por su ID
	    public Mascota getMascotaById(Integer id) {
	        return mascotaRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Mascota"));
	    }
	    
	    // Crear una nueva mascota
	    public Mascota createMascota(Mascota mascota) {
	        return mascotaRepository.save(mascota);
	    }
	    
	    // Actualizar una mascota existente
	    public Mascota updateMascota(Integer id, Mascota mascotaDetails) {
	        Mascota mascota = mascotaRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Mascota"));
	        
	        mascota.setNombre(mascotaDetails.getNombre());
	        
	        Mascota updatedMascota = mascotaRepository.save(mascota);
	        return updatedMascota;
	    }
	    
	    // Eliminar una mascota
	    public ResponseEntity<?> deleteMascota(Integer id) {
	        Mascota mascota = mascotaRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Mascota"));
	        
	        mascotaRepository.delete(mascota);
	        return ResponseEntity.ok().build();
	    }
}
