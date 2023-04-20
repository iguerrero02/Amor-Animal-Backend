package com.example.tiendaVeterinaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiendaVeterinaria.model.Producto;
import com.example.tiendaVeterinaria.service.ProductoService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/producto")
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;

	 @GetMapping(path = "/consultarTodos")
	    public List<Producto> consultarTodos(){
	        return productoService.consultarProductos();
	    }  
	
}
