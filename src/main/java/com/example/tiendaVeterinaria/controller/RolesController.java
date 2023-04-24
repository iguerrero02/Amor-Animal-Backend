package com.example.tiendaVeterinaria.controller;

import java.util.List;

//import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.RolEntity;
import com.example.tiendaVeterinaria.service.IRolesService;

@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"enctype", "Authorization"})
public class RolesController {
	
	@Autowired(required=false)
	private IRolesService rolesService;

	
	@GetMapping(path = "/todos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<RolEntity>>> listarRoles(){
		SingleResponse<List<RolEntity>> response = new SingleResponse<>();
		response = rolesService.consultarRoles();
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@PostMapping(path = "/nuevoRol", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<RolEntity>> crearRol(@RequestBody  RolEntity rol){
		SingleResponse<RolEntity> response = new SingleResponse<>();
		response = rolesService.crearRoles(rol);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@GetMapping(path = "/todosActivos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<RolEntity>>> listarRolesActivos(){
		SingleResponse<List<RolEntity>> response = new SingleResponse<>();
		response = rolesService.consultarRolesActivos();
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@PutMapping(path = "/actualizarEstatusRol", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<RolEntity>> actualizaEstatusRol(@RequestBody RolEntity rol){
		SingleResponse<RolEntity> response = new SingleResponse<>();
		response = rolesService.actualizarEstatusRol(rol);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
}
