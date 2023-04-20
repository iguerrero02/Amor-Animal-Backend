package com.example.tiendaVeterinaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.tiendaVeterinaria.model.Servicio;
import com.example.tiendaVeterinaria.service.ServicioService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

 // Obtener todos los servicios
    @GetMapping
    public List<Servicio> getServicios() {
        return servicioService.getAllServicios();
    }

    // Obtener un servicio por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Servicio> getServicioById(@PathVariable Integer id) {
        Servicio servicio = servicioService.getServicioById(id);
        if (servicio != null) {
            return new ResponseEntity<>(servicio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear un nuevo servicio
    @PostMapping
    public ResponseEntity<Servicio> createServicio(@RequestBody Servicio servicio) {
        servicioService.saveServicio(servicio);
        return new ResponseEntity<>(servicio, HttpStatus.CREATED);
    }

    // Actualizar un servicio existente por su ID
    @PutMapping("/{id}")
    public ResponseEntity<Servicio> updateServicio(@PathVariable Integer id, @RequestBody Servicio servicio) {
        Servicio existingServicio = servicioService.getServicioById(id);
        if (existingServicio != null) {
            servicio.setId_servicio(id);
            servicioService.saveServicio(servicio);
            return new ResponseEntity<>(servicio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un servicio existente por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicio(@PathVariable Integer id) {
        Servicio existingServicio = servicioService.getServicioById(id);
        if (existingServicio != null) {
            servicioService.deleteServicioById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}