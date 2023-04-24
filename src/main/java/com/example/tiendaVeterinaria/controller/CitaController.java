package com.example.tiendaVeterinaria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiendaVeterinaria.model.Adopcion;
import com.example.tiendaVeterinaria.model.Cita;
import com.example.tiendaVeterinaria.model.Mascota;
import com.example.tiendaVeterinaria.model.Servicio;
import com.example.tiendaVeterinaria.repository.CitaRepository;
import com.example.tiendaVeterinaria.repository.ServicioRepository;
import com.example.tiendaVeterinaria.service.CitaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/citas")
public class CitaController {
    
    @Autowired
    private CitaService citaService;
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Autowired
    private ServicioRepository servicioRepository;
    
    @GetMapping
    public List<Cita> getAllCitas() {
        return citaService.getAllCitas();
    }
    
    @GetMapping("/{id}")
    public Cita getCitaById(@PathVariable Integer id) {
        return citaService.getCitaById(id);
    }
    
    @PostMapping("")
    public ResponseEntity<Cita> addCita(@RequestBody Cita cita) {
        Servicio servicio = cita.getId_servicio();
        if (servicio != null && servicio.getId_servicio() != null) {
            Optional<Servicio> optionalServicio = servicioRepository.findById(servicio.getId_servicio());
            if (optionalServicio.isPresent()) {
            	cita.setId_servicio(optionalServicio.get());
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        Cita savedCita= citaRepository.save(cita);
        return ResponseEntity.ok(savedCita);
    }
    
    @PutMapping("/{id}")
    public void updateCita(@PathVariable Integer id, @RequestBody Cita cita) {
        cita.setId_cita(id);
        citaService.updateCita(cita);
    }
    
    @DeleteMapping("/{id}")
    public void deleteCita(@PathVariable Integer id) {
        citaService.deleteCita(id);
    }
}
