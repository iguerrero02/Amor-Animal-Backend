package com.example.tiendaVeterinaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.tiendaVeterinaria.model.Mascota;
import com.example.tiendaVeterinaria.service.MascotaService;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    // Endpoint para obtener todas las mascotas
    @GetMapping("")
    public ResponseEntity<List<Mascota>> getAllMascotas() {
        List<Mascota> mascotas = mascotaService.getAllMascotas();
        return new ResponseEntity<>(mascotas, HttpStatus.OK);
    }

    // Endpoint para obtener una mascota por id
    @GetMapping("/{id}")
    public ResponseEntity<Mascota> getMascotaById(@PathVariable Integer id) {
        Mascota mascota = mascotaService.getMascotaById(id);
        return new ResponseEntity<>(mascota, HttpStatus.OK);
    }

    // Endpoint para crear una mascota
    @PostMapping("")
    public ResponseEntity<Mascota> createMascota(@RequestBody Mascota mascota) {
        Mascota newMascota = mascotaService.createMascota(mascota);
        return new ResponseEntity<>(newMascota, HttpStatus.CREATED);
    }

    // Endpoint para actualizar una mascota
    @PutMapping("/{id}")
    public ResponseEntity<Mascota> updateMascota(@PathVariable Integer id, @RequestBody Mascota mascota) {
        Mascota updatedMascota = mascotaService.updateMascota(id, mascota);
        return new ResponseEntity<>(updatedMascota, HttpStatus.OK);
    }

    // Endpoint para eliminar una mascota por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMascota(@PathVariable Integer id) {
        mascotaService.deleteMascota(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

