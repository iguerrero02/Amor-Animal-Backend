package com.example.tiendaVeterinaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.tiendaVeterinaria.model.entity.ClienteEntity;
import com.example.tiendaVeterinaria.model.entity.ListaDeseosEntity;
import com.example.tiendaVeterinaria.model.entity.ProductosEntity;

public interface IListaDeseosRepository extends JpaRepository<ListaDeseosEntity, Integer>{

	List<ListaDeseosEntity> findByCliente(ClienteEntity cliente);
	
}
