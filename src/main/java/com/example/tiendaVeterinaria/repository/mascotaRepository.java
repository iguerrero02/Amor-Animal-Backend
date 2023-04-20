package com.example.tiendaVeterinaria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiendaVeterinaria.model.Mascota;


public interface mascotaRepository extends JpaRepository<Mascota, Integer>{


}
