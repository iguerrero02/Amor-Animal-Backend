package com.example.tiendaVeterinaria.service;

import java.util.List;

import com.example.tiendaVeterinaria.model.dto.ProductoImagen;
import com.example.tiendaVeterinaria.model.dto.RequestAltaProductoBean;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ProductosEntity;
import com.example.tiendaVeterinaria.model.entity.ProductosImagenEntity;

public interface IProductosImagenService {
	
	 SingleResponse<ProductosImagenEntity> guardarProductoImagen(RequestAltaProductoBean altaProductoImagen);
	 
	void guardarImagenes(List<ProductoImagen> imagenes, ProductosEntity producto);

	SingleResponse<List<ProductosImagenEntity>> consultarProductosImgID(ProductosEntity producto);
	
	void eliminaImagenes(ProductosImagenEntity producto);

	SingleResponse<ProductosEntity> agregarImagen(RequestAltaProductoBean producto);

}
