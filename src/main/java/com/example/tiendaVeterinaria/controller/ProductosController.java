package com.example.tiendaVeterinaria.controller;

import java.util.List;
import java.util.Optional;

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
import com.example.tiendaVeterinaria.model.entity.ProductosEntity;
import com.example.tiendaVeterinaria.repository.IProductosRepository;
import com.example.tiendaVeterinaria.service.IProductosService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = {"enctype", "Authorization"})
@RequestMapping("/Productos")
public class ProductosController {
	
	@Autowired
	private IProductosService productosService;
	
	@Autowired
	private IProductosRepository productoRepository;

	
	@GetMapping(path = "/todos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<ProductosEntity>>> listarProductos(){
		SingleResponse<List<ProductosEntity>> response = new SingleResponse<>();
		response = productosService.consultarProductos();
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@PostMapping(path = "/productosBuscar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<ProductosEntity>>> buscarProducto(@RequestBody  ProductosEntity producto){
		SingleResponse<List<ProductosEntity>> response = new SingleResponse<>();
		response = productosService.buscarProducto(producto);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}

	
	@PostMapping(path = "/nuevoProducto", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<ProductosEntity>> crearProducto(@RequestBody  ProductosEntity producto){
		SingleResponse<ProductosEntity> response = new SingleResponse<>();
		response = productosService.crearProductos(producto);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@GetMapping(path = "/todosEnStock", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<ProductosEntity>>> listarProductosActivos(){
		SingleResponse<List<ProductosEntity>> response = new SingleResponse<>();
		response = productosService.consultarProductosStock();
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@PostMapping(path = "/detalle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<Optional<ProductosEntity>>> detalleProducto(@RequestBody  ProductosEntity producto){
		SingleResponse<Optional<ProductosEntity>> response = new SingleResponse<>();
		response = productosService.detalleProducto(producto);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@PutMapping(path = "/actualizaProducto", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<ProductosEntity>> actualizaProducto(@RequestBody ProductosEntity producto){
		SingleResponse<ProductosEntity> response = new SingleResponse<>();
		response = productosService.actualizarProducto(producto);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
}
