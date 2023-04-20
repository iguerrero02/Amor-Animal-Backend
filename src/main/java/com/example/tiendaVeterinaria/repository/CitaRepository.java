package com.example.tiendaVeterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tiendaVeterinaria.model.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    // Métodos adicionales si son necesarios
}

