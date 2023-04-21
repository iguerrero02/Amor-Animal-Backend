package com.example.tiendaVeterinaria.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.tiendaVeterinaria.model.entity.CategoriasEntity;


public interface ICategoriaRepository extends JpaRepository<CategoriasEntity, Integer> {

	Optional<CategoriasEntity> findByNombreCategoriaIgnoreCase(String nombreCategoria);	

	List<CategoriasEntity> findByEstatus(Integer estatus);
	
	@Query("SELECT c FROM CategoriasEntity c WHERE CONCAT( c.idCategorias,' ',c.descripcionCategoria,' ',c.nombreCategoria,' ',c.detallesCategoria,' ',c.codigoReferencia,' ',c.estatus) LIKE %?1%")
	Page<CategoriasEntity> findAll(String search, Pageable pageable);
	
	@Query("SELECT c FROM CategoriasEntity c WHERE c.idCategoriaPadre = :idCategoriaPadre ")
	List<CategoriasEntity>  findByIdCategoriaPadre(Integer idCategoriaPadre);

	@Query("SELECT c FROM CategoriasEntity c WHERE c.idCategoriaPadre = NULL")
	List<CategoriasEntity>  findByIdCategoriaPadre();

}
