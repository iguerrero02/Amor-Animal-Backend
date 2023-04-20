package com.example.tiendaVeterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tiendaVeterinaria.model.Servicio;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
}

