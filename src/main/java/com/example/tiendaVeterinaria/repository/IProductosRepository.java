package com.example.tiendaVeterinaria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.tiendaVeterinaria.model.entity.ProductosEntity;

public interface IProductosRepository extends JpaRepository<ProductosEntity, Integer>{
	
	Optional<ProductosEntity> findByNombreProductoIgnoreCase(String nombreProducto);
	@Query("SELECT p FROM ProductosEntity p WHERE NOMBRE LIKE %:nombre% ")
	List<ProductosEntity> findByNombreProducto(@Param("nombre") String nombre);
//	List<ProductosEntity> findByStockGreaterThan(Integer cantidad);

}
