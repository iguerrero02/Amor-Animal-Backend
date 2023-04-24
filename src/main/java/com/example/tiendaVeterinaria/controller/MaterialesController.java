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
import com.example.tiendaVeterinaria.model.entity.MaterialesEntity;
import com.example.tiendaVeterinaria.service.IMaterialesService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = {"enctype", "Authorization"})
@RequestMapping("/Materiales")
public class MaterialesController {
	
	@Autowired
	private IMaterialesService MaterialesService;

	
	@GetMapping(path = "/todos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<MaterialesEntity>>> listarMateriales(){
		SingleResponse<List<MaterialesEntity>> response = new SingleResponse<>();
		response = MaterialesService.consultarMateriales();
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@PostMapping(path = "/nuevoMaterial", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<MaterialesEntity>> crearMaterial(@RequestBody  MaterialesEntity material){
		SingleResponse<MaterialesEntity> response = new SingleResponse<>();
		response = MaterialesService.crearMateriales(material);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@GetMapping(path = "/todosActivos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<MaterialesEntity>>> listarMaterialesActivos(){
		SingleResponse<List<MaterialesEntity>> response = new SingleResponse<>();
		response = MaterialesService.consultarMaterialesActivos();
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@PutMapping(path = "/actualizarMaterial", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<MaterialesEntity>> actualizaMaterial(@RequestBody MaterialesEntity material){
		SingleResponse<MaterialesEntity> response = new SingleResponse<>();
		response = MaterialesService.actualizarMaterial(material);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
}
