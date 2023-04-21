package com.example.tiendaVeterinaria.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.tiendaVeterinaria.model.entity.OfertaEntity;

public interface IOfertaRepository extends JpaRepository<OfertaEntity, Integer> {

	List<OfertaEntity> findByEstatus(Integer estatus);
    List<OfertaEntity> findByFechaFinBeforeAndEstatusNot(LocalDateTime fechaFin, Integer estatus);
    
    @Query("SELECT o FROM OfertaEntity o INNER JOIN o.producto p WHERE p.idProducto = :idProducto ")
    Optional<OfertaEntity> findByProductoAndIdProducto(Integer idProducto);
	
}
