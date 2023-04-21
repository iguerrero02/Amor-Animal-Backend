package com.example.tiendaVeterinaria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiendaVeterinaria.exceptions.BusinessException;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.MaterialesEntity;
import com.example.tiendaVeterinaria.repository.IMaterialesRepository;

@Service
public class MaterialesService implements IMaterialesService{
	
	
	private static final Logger log = LoggerFactory.getLogger(MaterialesService.class);
	public static final Integer ESTATUS_ACTIVO = 1;
	
	@Autowired
	private IMaterialesRepository materialRepository;


	@Transactional
	@Override
	public SingleResponse<List<MaterialesEntity>> consultarMateriales() {
		List<MaterialesEntity> listaMateriales = new ArrayList<>();
		
		try {
			listaMateriales = materialRepository.findAll();
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los Materiales en la BD");
		}
		
		if(!listaMateriales.isEmpty()) {
			SingleResponse<List<MaterialesEntity>> response = new SingleResponse<>();
			response.setOk(true);
			response.setMensaje("Se ha obtenido la lista de Materiales exitosamente");
			response.setResponse(listaMateriales);
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registros de Materiales en la BD");
	}


	@Transactional
	@Override
	public SingleResponse<MaterialesEntity> crearMateriales(MaterialesEntity material) {
		Optional<MaterialesEntity> materialOp = Optional.empty();
		try {
			materialOp = materialRepository.findByNombreMaterialIgnoreCase(material.getNombreMaterial());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los Materiales en la BD");
		}
		if(materialOp.isPresent()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "El  material " + material.getNombreMaterial() +" ya existe en la BD");
		}
		material.setEstatus(1);
		try {
			material = materialRepository.save(material);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los Materiales en la BD");
		}
		SingleResponse<MaterialesEntity> response = new SingleResponse<>();
		response.setOk(true);
		response.setMensaje("Se ha guardado el material " + material.getNombreMaterial() +" exitosamente.");
		response.setResponse(material);
		return response;
	}


	@Override
	public SingleResponse<List<MaterialesEntity>> consultarMaterialesActivos() {
		List<MaterialesEntity> listaMateriales = new ArrayList<>();
		
		try {
			listaMateriales = materialRepository.findByEstatus(ESTATUS_ACTIVO);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los Materiales en la BD");
		}
		
		if(!listaMateriales.isEmpty()) {
			SingleResponse<List<MaterialesEntity>> response = new SingleResponse<>();
			response.setOk(true);
			response.setMensaje("Se ha obtenido la lista de Materiales activos exitosamente");
			response.setResponse(listaMateriales);
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registros de Materiales activos en la BD");
	}


	@Override
	public SingleResponse<MaterialesEntity> actualizarMaterial(MaterialesEntity material) {
		Optional<MaterialesEntity> materialOp = Optional.empty();
		try {
			materialOp = materialRepository.findById(material.getIdMaterial());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los Materiales en la BD");
		}
		if(!materialOp.isPresent()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "El  material con Id " + material.getIdMaterial() +" no existe en la BD");
		}
		MaterialesEntity materialUpdate = materialOp.get();
		materialUpdate.setEstatus(material.getEstatus());
		materialUpdate.setDescripcionMaterial(material.getDescripcionMaterial());
		materialUpdate.setNombreMaterial(material.getNombreMaterial());
		try {
			materialUpdate = materialRepository.save(materialUpdate);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los Materiales en la BD");
		}
		SingleResponse<MaterialesEntity> response = new SingleResponse<>();
		response.setOk(true);
		response.setMensaje("Se ha actualizado el material con Id" + materialUpdate.getIdMaterial() +" exitosamente.");
		response.setResponse(materialUpdate);
		return response;
	}

}
