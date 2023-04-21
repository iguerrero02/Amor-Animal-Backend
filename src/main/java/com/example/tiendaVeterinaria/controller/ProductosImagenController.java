package com.example.tiendaVeterinaria.controller;



import java.util.List;

import javax.validation.Valid;

//import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiendaVeterinaria.model.dto.RequestAltaProductoBean;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ProductosEntity;
import com.example.tiendaVeterinaria.model.entity.ProductosImagenEntity;
import com.example.tiendaVeterinaria.service.IProductosImagenService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"enctype", "Authorization"})
@RequestMapping("/ProductosImg")
public class ProductosImagenController {
	

	@Autowired
	private IProductosImagenService productosImagenService;

	
	@PostMapping(path = "/crearProductoImagen", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<ProductosImagenEntity>> crearProductoImagen(@RequestBody @Valid RequestAltaProductoBean producto) {
		// Se instancia el objeto de respuesta
		SingleResponse<ProductosImagenEntity> response = new SingleResponse<>();
			response = productosImagenService.guardarProductoImagen(producto);
			return new ResponseEntity<>(response, HttpStatus.CREATED);	//Se crea respuesta Ok		
	}
	
	@PostMapping(path = "/buscarImagenesProd", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<ProductosImagenEntity>>> listarProductosImg(@RequestBody ProductosEntity producto){
		SingleResponse<List<ProductosImagenEntity>> response = new SingleResponse<>();
		response = productosImagenService.consultarProductosImgID(producto);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@PostMapping(path = "/agregarImagen", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<ProductosEntity>> agregarImagen(@RequestBody @Valid RequestAltaProductoBean producto) {
		// Se instancia el objeto de respuesta
		SingleResponse<ProductosEntity> response = new SingleResponse<>();
			response = productosImagenService.agregarImagen(producto);
			return new ResponseEntity<>(response, HttpStatus.CREATED);	//Se crea respuesta Ok
	}
	
	@DeleteMapping(path = "/elimina", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<String>> eliminarImagenes(@RequestBody ProductosImagenEntity productoImg){
		productosImagenService.eliminaImagenes(productoImg);
        SingleResponse<String> response = new SingleResponse<>();
        response.setMensaje("El producto ha sido eliminado exitosamente");
        response.setOk(true);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	
}
