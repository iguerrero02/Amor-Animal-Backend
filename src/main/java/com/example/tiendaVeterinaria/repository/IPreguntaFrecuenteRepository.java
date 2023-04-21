package com.example.tiendaVeterinaria.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.tiendaVeterinaria.model.entity.PreguntaFrecuente;

public interface IPreguntaFrecuenteRepository extends JpaRepository<PreguntaFrecuente, Integer>{
	
	List<PreguntaFrecuente> findByEstatus(Integer idEstatus);
	
	@Query("SELECT p FROM PreguntaFrecuente p WHERE CONCAT(p.pregunta, ' ' , p.respuesta) LIKE %?1%")
	Page<PreguntaFrecuente> findAll(String search, Pageable pageable);
	

}
