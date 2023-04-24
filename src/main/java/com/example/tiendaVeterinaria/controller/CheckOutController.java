package com.example.tiendaVeterinaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.tiendaVeterinaria.model.dto.CheckOutInfo;
import com.example.tiendaVeterinaria.model.dto.ResponseListarCarrito;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.service.ICheckOutService;

@RestController
@RequestMapping("/checkout")
@CrossOrigin(origins = "*", allowedHeaders = {"enctype", "Authorization"})
public class CheckOutController {
	
	@Autowired
	private ICheckOutService checkOutService;

	

	@GetMapping(path = "/mostrar/{idCliente}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<CheckOutInfo>> mostrarCheckout(@PathVariable("idCliente") Integer idCliente){
		SingleResponse<CheckOutInfo> response = new SingleResponse<>();
		response = checkOutService.prepararCheckOut(idCliente);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
}
