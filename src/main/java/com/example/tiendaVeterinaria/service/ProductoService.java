package com.example.tiendaVeterinaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tiendaVeterinaria.model.Producto;
import com.example.tiendaVeterinaria.repository.ProductoRepository;

@Service
public class ProductoService implements IProductoService {

	@Autowired
	private ProductoRepository productoRepo;

	@Override
	public List<Producto> consultarProductos() {
		// TODO Auto-generated method stub
		return (List<Producto>)productoRepo.findAll();
	}
	
}
