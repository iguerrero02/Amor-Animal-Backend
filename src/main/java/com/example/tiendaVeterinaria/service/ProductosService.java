package com.example.tiendaVeterinaria.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiendaVeterinaria.exceptions.BusinessException;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ProductosEntity;
import com.example.tiendaVeterinaria.repository.IProductosRepository;

@Service
public class ProductosService implements IProductosService{
	
	
	private static final Logger log = LoggerFactory.getLogger(ProductosService.class);
	
	@Autowired
	private IProductosRepository productoRepository;
	
	


	@Transactional
	@Override
	public SingleResponse<List<ProductosEntity>> consultarProductos() {
		List<ProductosEntity> listaProductos = new ArrayList<>();
		
		try {
			listaProductos = productoRepository.findAll();
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los Productos en la BD");
		}
		
		if(!listaProductos.isEmpty()) {
			SingleResponse<List<ProductosEntity>> response = new SingleResponse<>();
			response.setOk(true);
			response.setMensaje("Se ha obtenido la lista de Productos exitosamente");
			response.setResponse(listaProductos);
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registros de Productos en la BD");
	}


	@Transactional
	@Override
	public SingleResponse<ProductosEntity> crearProductos(ProductosEntity producto) {
		Optional<ProductosEntity> productoOp = Optional.empty();
		try {
			productoOp = productoRepository.findByNombreProductoIgnoreCase(producto.getNombreProducto());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los Productos en la BD");
		}
		if(productoOp.isPresent()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "El  producto " + producto.getNombreProducto() +" ya existe en la BD");
		}
		
		try {
			producto = productoRepository.save(producto);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los Productos en la BD");
		}
		SingleResponse<ProductosEntity> response = new SingleResponse<>();
		response.setOk(true);
		response.setMensaje("Se ha guardado el producto " + producto.getNombreProducto() +" exitosamente.");
		response.setResponse(producto);
		return response;
	}


	@Override
	public SingleResponse<List<ProductosEntity>> consultarProductosStock() {
	
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registros de Productos en Stock en la BD");
	}
	

	@Override
	public SingleResponse<List<ProductosEntity>> buscarProducto(ProductosEntity producto) {
		List<ProductosEntity> productoDetalle = new ArrayList<>();
		
		try {
			productoDetalle = productoRepository.findByNombreProducto(producto.getNombreProducto());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los Productos en la BD");
		}
		if(!productoDetalle.isEmpty()) {
			SingleResponse<List<ProductosEntity>> response = new SingleResponse<>();
			response.setOk(true);
			response.setMensaje("Se ha obtenido la lista de Productos exitosamente");
			response.setResponse(productoDetalle);
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registros de Productos en la BD");
	}
	
	@Override
	public SingleResponse<Optional<ProductosEntity>> detalleProducto(ProductosEntity producto) {
		Optional<ProductosEntity> productoDetalle = Optional.empty();
		
		try {
			productoDetalle = productoRepository.findById(producto.getIdProducto());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los Productos en la BD");
		}
		
		if(productoDetalle.isPresent()) {
			SingleResponse<Optional<ProductosEntity>> response = new SingleResponse<>();
			response.setOk(true);
			response.setMensaje("Se ha obtenido la lista de Productos en Stock exitosamente");
			response.setResponse(productoDetalle);
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registros de Productos en la BD");
	}
	
	@Override
	public SingleResponse<ProductosEntity> actualizarProducto(ProductosEntity producto) {
		Optional<ProductosEntity> productoOp = Optional.empty();
		try {
			productoOp = productoRepository.findById(producto.getIdProducto());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los productoes en la BD");
		}
		if(!productoOp.isPresent()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "El  producto con Id " + producto.getIdProducto() +" no existe en la BD");
		}
		ProductosEntity productoUpdate = productoOp.get();
//		productoUpdate.setAltoExterior(producto.getAltoExterior());
//		productoUpdate.setAltoInterior(producto.getAltoInterior());
//		productoUpdate.setAnchoExterior(producto.getAnchoExterior());
//		productoUpdate.setAnchoInterior(producto.getAnchoInterior());
//		productoUpdate.setLargoExterior(producto.getLargoExterior());
//		productoUpdate.setLargoInterior(producto.getLargoInterior());
//		productoUpdate.setCodigoReferencia(producto.getCodigoReferencia());
		productoUpdate.setDescripcionBreve(producto.getDescripcionBreve());
//		productoUpdate.setFechaModificacion(LocalDateTime.now());
		productoUpdate.setNombreProducto(producto.getNombreProducto());
//		productoUpdate.setPeso(producto.getPeso());
//		productoUpdate.setPrecioCompra(producto.getPrecioCompra());
		productoUpdate.setPrecioVenta(producto.getPrecioVenta());
		//productoUpdate.setStock(producto.getStock());
		productoUpdate.setEstatus(producto.getEstatus());

		try {
			productoUpdate = productoRepository.save(productoUpdate);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los productoes en la BD");
		}
		SingleResponse<ProductosEntity> response = new SingleResponse<>();
		response.setOk(true);
		response.setMensaje("Se ha actualizado el producto con Id" + productoUpdate.getIdProducto() +" exitosamente.");
		response.setResponse(productoUpdate);
		return response;
	}


}
