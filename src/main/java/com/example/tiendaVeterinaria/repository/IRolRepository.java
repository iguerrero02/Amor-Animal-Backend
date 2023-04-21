package com.example.tiendaVeterinaria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiendaVeterinaria.model.entity.RolEntity;

public interface IRolRepository extends JpaRepository<RolEntity, Integer>{
	
	Optional<RolEntity> findByNombreRolIgnoreCase(String nombreRol);
	List<RolEntity> findByEstatus(Integer estatus);
	Optional<RolEntity> findByIdRol(Integer id);

}
