package com.example.tiendaVeterinaria.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.tiendaVeterinaria.model.Adopcion;



public interface AdopcionRepository extends JpaRepository<Adopcion, Integer>{

}

