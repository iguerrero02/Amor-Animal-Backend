package com.example.tiendaVeterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiendaVeterinaria.model.entity.OrdenDetalleEntity;

public interface IOrdenDetalleRepository extends JpaRepository<OrdenDetalleEntity, Integer> {

}
