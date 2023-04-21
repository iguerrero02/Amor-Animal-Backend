package com.example.tiendaVeterinaria.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tiendaVeterinaria.model.Mascota;

@Repository
public interface mascotaRepository extends JpaRepository<Mascota, Integer>{


}
