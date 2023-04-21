package com.example.tiendaVeterinaria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiendaVeterinaria.model.entity.MaterialesEntity;

public interface IMaterialesRepository extends JpaRepository<MaterialesEntity, Integer>{
	
	Optional<MaterialesEntity> findByNombreMaterialIgnoreCase(String nombreMaterial);
	List<MaterialesEntity> findByEstatus(Integer estatus);

}
