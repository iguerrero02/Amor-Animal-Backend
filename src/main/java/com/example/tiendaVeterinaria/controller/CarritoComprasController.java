package com.example.tiendaVeterinaria.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiendaVeterinaria.model.dto.CarroComprasRequest;
import com.example.tiendaVeterinaria.model.dto.ResponseListarCarrito;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.CarroComprasEntity;
import com.example.tiendaVeterinaria.service.ICarritoCompraService;

@RestController
@RequestMapping("/carroCompras")
@CrossOrigin(origins = "*", allowedHeaders = {"enctype", "Authorization"})
public class CarritoComprasController {
	
	@Autowired
	private ICarritoCompraService carritoCompraService;
	
	@PostMapping(path = "/agregarProducto", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<CarroComprasEntity>> agregarProducto(@RequestBody CarroComprasRequest request){
		SingleResponse<CarroComprasEntity> response = new SingleResponse<>();
		response = carritoCompraService.agregarProducto(request);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@GetMapping(path = "/listarCarrito/{idCliente}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<ResponseListarCarrito>> listarCarrito(@PathVariable("idCliente") Integer idCliente){
		SingleResponse<ResponseListarCarrito> response = new SingleResponse<>();
		response = carritoCompraService.mostrarCarrito(idCliente);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@PutMapping(path = "/actualizarCantidad", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<ResponseListarCarrito>> actualizarCantidad(@RequestBody CarroComprasRequest request){
		SingleResponse<ResponseListarCarrito> response = new SingleResponse<>();
		response = carritoCompraService.actualizarCantidad(request);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@DeleteMapping(path = "/eliminarProducto/{idCarrito}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<ResponseListarCarrito>> eliminarProducto(@PathVariable("idCarrito") Integer idCarrito){
		SingleResponse<ResponseListarCarrito> response = new SingleResponse<>();
		response = carritoCompraService.eliminarProducto(idCarrito);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}

}
