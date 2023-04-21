package com.example.tiendaVeterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiendaVeterinaria.model.entity.ProductosEntity;

public interface IProductoRepository  extends JpaRepository<ProductosEntity, Integer>  {

}
