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
import com.example.tiendaVeterinaria.model.Mascota;
import com.example.tiendaVeterinaria.repository.AdopcionRepository;
import com.example.tiendaVeterinaria.repository.mascotaRepository;
import com.example.tiendaVeterinaria.service.AdopcionService;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/adopciones/")
public class AdopcionController {
    
    @Autowired
    private AdopcionService adopcionService;
    
    @Autowired
    private mascotaRepository  mascotaRepository;
    
    @Autowired
    private  AdopcionRepository adopcionRepository;
    
    @GetMapping("")
    public List<Adopcion> findAll() {
        return adopcionService.findAll();
    }

    @GetMapping("/{id}")
    public Adopcion findById(@PathVariable Integer id) {
        return adopcionService.findById(id);
    }

    @PostMapping("")
    public ResponseEntity<Adopcion> save(@RequestBody Adopcion adopcion) {
        Mascota mascota = adopcion.getMascota();
        if (mascota != null && mascota.getId_mascota() != null) {
            Optional<Mascota> optionalMascota = mascotaRepository.findById(mascota.getId_mascota());
            if (optionalMascota.isPresent()) {
                adopcion.setMascota(optionalMascota.get());
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        Adopcion savedAdopcion = adopcionRepository.save(adopcion);
        return ResponseEntity.ok(savedAdopcion);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Adopcion> update(@PathVariable Integer id, @RequestBody Adopcion adopcion) {
        Adopcion adopcionExistente = adopcionRepository.findById(id).orElse(null);
        if (adopcionExistente != null) {
            adopcionExistente.setNombre(adopcion.getNombre());
            adopcionExistente.setApellido(adopcion.getApellido());
            adopcionExistente.setEdad(adopcion.getEdad());
            adopcionExistente.setDireccion(adopcion.getDireccion());
            adopcionExistente.setCiudad(adopcion.getCiudad());
            adopcionExistente.setCodigo_postal(adopcion.getCodigo_postal());
            Mascota mascota = adopcion.getMascota();
            if (mascota != null && mascota.getId_mascota() != null) {
                Optional<Mascota> optionalMascota = mascotaRepository.findById(mascota.getId_mascota());
                if (optionalMascota.isPresent() && !optionalMascota.get().getId_mascota().equals(adopcionExistente.getMascota().getId_mascota())) {
                    adopcionExistente.setMascota(optionalMascota.get());
                } else {
                    return ResponseEntity.badRequest().build();
                }
            } else {
                adopcionExistente.setMascota(null);
            }
            Adopcion updatedAdopcion = adopcionRepository.save(adopcionExistente);
            return ResponseEntity.ok(updatedAdopcion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        adopcionService.deleteById(id);
    }
}

