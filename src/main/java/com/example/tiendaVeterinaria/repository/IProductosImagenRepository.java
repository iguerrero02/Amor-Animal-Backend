package com.example.tiendaVeterinaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.example.tiendaVeterinaria.model.entity.ProductosEntity;
import com.example.tiendaVeterinaria.model.entity.ProductosImagenEntity;

public interface IProductosImagenRepository extends JpaRepository<ProductosImagenEntity, Integer>{
	
	List<ProductosImagenEntity> findByIdProducto(ProductosEntity producto);



}
