package com.example.tiendaVeterinaria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.tiendaVeterinaria.model.entity.ClienteEntity;
import com.example.tiendaVeterinaria.model.entity.DireccionEntity;
import com.example.tiendaVeterinaria.model.entity.UsuarioEntity;

public interface IClienteRepository extends JpaRepository<ClienteEntity, Integer> {
	
	Optional<ClienteEntity> findBycorreoElectronicoIgnoreCase(String id);
	Optional<ClienteEntity> findBycodigoVerificacionIgnoreCase(String id);
	List<ClienteEntity> findByEstatusOrderByApellidoPaternoAscApellidoMaternoAscNombreAsc(Integer estatus);
	Page<ClienteEntity> findByEstatus(Integer estatus, Pageable pageable);
	@Query("SELECT u FROM ClienteEntity u WHERE CONCAT( u.nombre,' ',u.apellidoPaterno,' ',"
			+ "u.apellidoMaterno,' ' , u.correoElectronico) LIKE %?1%")		
	Page<ClienteEntity> findAll(String search, Pageable pageable);
	Optional<ClienteEntity> findByCorreoElectronicoIgnoreCaseAndEstatus(String correo, Integer estatus);
	
	@Query("SELECT d FROM ClienteEntity d WHERE CODIGO_VERIFICACION =:id ")
	Optional<ClienteEntity> findByClienteCodigo(@Param("id") String codigo);
}
