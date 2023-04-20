package com.example.tiendaVeterinaria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tiendaVeterinaria.model.Mascota;

@Repository
public interface mascotaRepository extends JpaRepository<Mascota, Integer>{


}
