package com.example.tiendaVeterinaria.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.tiendaVeterinaria.model.Adopcion;


@Repository
public interface AdopcionRepository extends JpaRepository<Adopcion, Integer>{

}

