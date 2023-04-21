package com.example.tiendaVeterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiendaVeterinaria.model.entity.ClienteEntity;

public interface IClienteEntity extends JpaRepository<ClienteEntity, Integer>{

}
