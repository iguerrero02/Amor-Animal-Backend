package com.example.tiendaVeterinaria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiendaVeterinaria.model.entity.ColoresEntity;

public interface IColorRepository extends JpaRepository<ColoresEntity, Integer> {
	
	Optional<ColoresEntity> findByNombreColorIgnoreCase(String nombreColor);
	List<ColoresEntity> findByEstatus(Integer estatus);

}
