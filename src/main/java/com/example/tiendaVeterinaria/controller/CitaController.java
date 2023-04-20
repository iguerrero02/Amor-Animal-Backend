package com.example.tiendaVeterinaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiendaVeterinaria.model.Cita;
import com.example.tiendaVeterinaria.service.CitaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/citas")
public class CitaController {
    
    @Autowired
    private CitaService citaService;
    
    @GetMapping
    public List<Cita> getAllCitas() {
        return citaService.getAllCitas();
    }
    
    @GetMapping("/{id}")
    public Cita getCitaById(@PathVariable Integer id) {
        return citaService.getCitaById(id);
    }
    
    @PostMapping
    public void addCita(@RequestBody Cita cita) {
        citaService.addCita(cita);
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
