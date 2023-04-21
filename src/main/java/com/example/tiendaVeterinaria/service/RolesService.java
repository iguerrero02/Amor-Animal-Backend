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
import com.example.tiendaVeterinaria.model.entity.RolEntity;
import com.example.tiendaVeterinaria.repository.IRolRepository;

@Service
public class RolesService implements IRolesService{
	
	
	private static final Logger log = LoggerFactory.getLogger(RolesService.class);
	public static final Integer ESTATUS_ACTIVO = 1;
	
	@Autowired
	private IRolRepository rolRepository;


	@Transactional
	@Override
	public SingleResponse<List<RolEntity>> consultarRoles() {
		List<RolEntity> listaRoles = new ArrayList<>();
		
		try {
			listaRoles = rolRepository.findAll();
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los roles en la BD");
		}
		
		if(!listaRoles.isEmpty()) {
			SingleResponse<List<RolEntity>> response = new SingleResponse<>();
			response.setOk(true);
			response.setMensaje("Se ha obtenido la lista de roles exitosamente");
			response.setResponse(listaRoles);
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registros de roles en la BD");
	}


	@Transactional
	@Override
	public SingleResponse<RolEntity> crearRoles(RolEntity rol) {
		Optional<RolEntity> rolOp = Optional.empty();
		try {
			rolOp = rolRepository.findByNombreRolIgnoreCase(rol.getNombreRol());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los roles en la BD");
		}
		if(rolOp.isPresent()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "El  rol " + rol.getNombreRol() +" ya existe en la BD");
		}
		rol.setEstatus(1);
		try {
			rol = rolRepository.save(rol);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los roles en la BD");
		}
		SingleResponse<RolEntity> response = new SingleResponse<>();
		response.setOk(true);
		response.setMensaje("Se ha guardado el rol " + rol.getNombreRol() +" exitosamente.");
		response.setResponse(rol);
		return response;
	}


	@Override
	public SingleResponse<List<RolEntity>> consultarRolesActivos() {
		List<RolEntity> listaRoles = new ArrayList<>();
		
		try {
			listaRoles = rolRepository.findByEstatus(ESTATUS_ACTIVO);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los roles en la BD");
		}
		
		if(!listaRoles.isEmpty()) {
			SingleResponse<List<RolEntity>> response = new SingleResponse<>();
			response.setOk(true);
			response.setMensaje("Se ha obtenido la lista de roles activos exitosamente");
			response.setResponse(listaRoles);
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registros de roles activos en la BD");
	}


	@Override
	public SingleResponse<RolEntity> actualizarEstatusRol(RolEntity rol) {
		Optional<RolEntity> rolOp = Optional.empty();
		try {
			rolOp = rolRepository.findById(rol.getIdRol());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los roles en la BD");
		}
		if(!rolOp.isPresent()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "El  rol con Id " + rol.getIdRol() +" no existe en la BD");
		}
		RolEntity rolUpdate = rolOp.get();
		rolUpdate.setEstatus(rol.getEstatus());
		try {
			rolUpdate = rolRepository.save(rolUpdate);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los roles en la BD");
		}
		SingleResponse<RolEntity> response = new SingleResponse<>();
		response.setOk(true);
		response.setMensaje("Se ha actualizado el rol con Id" + rolUpdate.getIdRol() +" exitosamente.");
		response.setResponse(rolUpdate);
		return response;
	}

}
