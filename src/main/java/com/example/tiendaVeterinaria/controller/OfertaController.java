package com.example.tiendaVeterinaria.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.OfertaEntity;
import com.example.tiendaVeterinaria.service.IOfertaService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = {"enctype", "Authorization"})
@RequestMapping("/oferta")
public class OfertaController {

	@Autowired
	private IOfertaService ofertaService;
	
	@GetMapping(value = "/consultarTodos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<OfertaEntity>>> consultarTodos(){
		SingleResponse<List<OfertaEntity>> response = ofertaService.consultarTodosOferta();
		return new ResponseEntity<SingleResponse<List<OfertaEntity>>>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/guardar",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<OfertaEntity>> actualizarOfertaEstatus(@RequestBody OfertaEntity oferta, @RequestParam Integer idProducto){
		SingleResponse<OfertaEntity> response = ofertaService.guardarOferta(oferta, idProducto);
		return new ResponseEntity<SingleResponse<OfertaEntity>>(response, HttpStatus.OK);
	}
	
	@PutMapping(value = "/actualizar",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<OfertaEntity>> actualizarOferta(@RequestBody OfertaEntity oferta, @RequestParam Integer idProducto){
		SingleResponse<OfertaEntity> response = ofertaService.actualizarOferta(oferta, idProducto);
		return new ResponseEntity<SingleResponse<OfertaEntity>>(response, HttpStatus.OK);
	}
	
	@PutMapping(value = "/actualizarActivo",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<OfertaEntity>> actualizarOfertaEstatus(@RequestParam Integer idOferta,
			@RequestParam Integer estatus){
		SingleResponse<OfertaEntity> response = ofertaService.actualizarOfertaEstatus(idOferta, estatus);
		return new ResponseEntity<SingleResponse<OfertaEntity>>(response, HttpStatus.OK);
	}
		
	@GetMapping(value = "/consultarTodosActivos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<OfertaEntity>>> consultarTodosActivos(){
		SingleResponse<List<OfertaEntity>> response = ofertaService.consultarTodosActivos();
		return new ResponseEntity<SingleResponse<List<OfertaEntity>>>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/consultarPorIdProducto",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<OfertaEntity>> buscarPorIdProducto(@RequestParam Integer idProducto){
		SingleResponse<OfertaEntity> response = ofertaService.busquedaPorIdProducto(idProducto);
		return new ResponseEntity<SingleResponse<OfertaEntity>>(response, HttpStatus.OK);
	}
}
