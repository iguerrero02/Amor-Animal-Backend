package com.example.tiendaVeterinaria.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiendaVeterinaria.model.entity.DireccionEntity;
import com.example.tiendaVeterinaria.model.entity.UsuarioEntity;

public interface IDireccionRepository extends JpaRepository<DireccionEntity, Integer>{

	@Query("SELECT d FROM DireccionEntity d WHERE ID_CLIENTE =:id ")
	List<DireccionEntity> findByDireccionCliente(@Param("id") Integer id,  Pageable pageable);
	
	@Query("SELECT d FROM DireccionEntity d WHERE ID_CLIENTE =:id ")
	List<DireccionEntity> findByDireccionClienteTodas(@Param("id") Integer id);
	
	@Query("SELECT d FROM DireccionEntity d WHERE ID_CLIENTE =:id ")
	Optional<DireccionEntity> findByClienteAll(@Param("id") Integer id);
	
	@Query("SELECT d FROM DireccionEntity d WHERE ID_CLIENTE =:id and predeterminado = 1")
	DireccionEntity findBDireccionPredeterminada(@Param("id") Integer id);
	
	@Modifying
	@Query("DELETE  FROM DireccionEntity  WHERE ID_CLIENTE =:id")
	void deleteCliente(@Param("id") Integer id);
	
}
