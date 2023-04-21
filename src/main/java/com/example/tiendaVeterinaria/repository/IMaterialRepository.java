package com.example.tiendaVeterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiendaVeterinaria.model.entity.MaterialesEntity;


public interface IMaterialRepository extends JpaRepository<MaterialesEntity, Integer> {

}
