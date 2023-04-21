package com.example.tiendaVeterinaria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.tiendaVeterinaria.model.entity.CarroComprasEntity;
import com.example.tiendaVeterinaria.model.entity.ClienteEntity;
import com.example.tiendaVeterinaria.model.entity.ProductosEntity;

public interface ICarroComprasRepository extends JpaRepository<CarroComprasEntity, Integer>{
	Optional<CarroComprasEntity> findByClienteAndProducto(ClienteEntity cliente, ProductosEntity producto);
	List<CarroComprasEntity> findByCliente(ClienteEntity cliente);
	@Modifying
	@Query("DELETE CarroComprasEntity c WHERE c.cliente.idCliente = ?1")
	void deleteByCliente(Integer idCliente);

}
