package com.example.tiendaVeterinaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiendaVeterinaria.model.dto.ListaDeseosProductosDTO;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ListaDeseosEntity;
import com.example.tiendaVeterinaria.service.IListaDeseosService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"enctype", "Authorization"})
@RequestMapping("/listaDeseos")
public class ListaDeseosController {

	@Autowired
	IListaDeseosService listaDeseosService;

	@PostMapping(value = "/consultarTodos",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<ListaDeseosProductosDTO>>> consultarListaDeseo(
		@RequestParam Integer idCliente) {
		SingleResponse<List<ListaDeseosProductosDTO>> response = listaDeseosService.consultarTodos(idCliente);
		return new ResponseEntity<SingleResponse<List<ListaDeseosProductosDTO>>>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/guardarListaDeseos",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<ListaDeseosEntity>> guardarEnListaDeseos(@RequestParam Integer idCliente,
	@RequestParam Integer idProducto ){
		SingleResponse<ListaDeseosEntity> response = listaDeseosService.guardarListaDeseo(idCliente, idProducto);
		return new ResponseEntity<SingleResponse<ListaDeseosEntity>>(response,HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/eliminarDeseo",produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<ListaDeseosEntity>> eliminarListaDeseos(@RequestParam Integer idListaDeseo){
		SingleResponse<ListaDeseosEntity> response = listaDeseosService.eliminarListaDeseo(idListaDeseo);
		return new ResponseEntity<SingleResponse<ListaDeseosEntity>>(response,HttpStatus.OK);
	}
	
	
	

}
