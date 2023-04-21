package com.example.tiendaVeterinaria.service;

import java.util.List;
import java.util.Optional;

import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ProductosEntity;
import com.example.tiendaVeterinaria.model.entity.RolEntity;

public interface IProductosService {
	
	SingleResponse<List<ProductosEntity>> consultarProductos();
	SingleResponse<ProductosEntity> crearProductos(ProductosEntity producto);
	SingleResponse<List<ProductosEntity>> consultarProductosStock();
	SingleResponse<Optional<ProductosEntity>> detalleProducto(ProductosEntity producto);
	SingleResponse<List<ProductosEntity>> buscarProducto(ProductosEntity producto);
	SingleResponse<ProductosEntity> actualizarProducto(ProductosEntity producto);

}
