package com.example.tiendaVeterinaria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.tiendaVeterinaria.model.entity.ReseniaEntity;

public interface IReseniaRepository extends JpaRepository<ReseniaEntity, Integer>{

	@Query("SELECT r FROM ReseniaEntity r WHERE r.cliente.idCliente = :idCliente AND r.producto.idProducto = :idProducto ")
	Optional<ReseniaEntity> findByClienteProducto(@Param("idCliente") Integer idCliente, @Param("idProducto") Integer idProducto);
	
	@Query("SELECT r FROM ReseniaEntity r WHERE r.cliente.idCliente = :idCliente")
	List<ReseniaEntity> findByCliente(Integer idCliente);
	
//	@Query("SELECT d FROM ReseniaEntity d WHERE ID_CLIENTE =:idCliente")
//	Optional<List<ReseniaEntity>> findByReseniaCliente(@Param("idCliente") Integer idCliente);
	
}
