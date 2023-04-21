package com.example.tiendaVeterinaria.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiendaVeterinaria.exceptions.BusinessException;
import com.example.tiendaVeterinaria.model.dto.ProductoImagen;
import com.example.tiendaVeterinaria.model.dto.RequestAltaProductoBean;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ProductosEntity;
import com.example.tiendaVeterinaria.model.entity.ProductosImagenEntity;
import com.example.tiendaVeterinaria.repository.IProductosImagenRepository;
import com.example.tiendaVeterinaria.repository.IProductosRepository;

@Service
public class ProductosImagenService implements IProductosImagenService{
	
	
	private static final Logger log = LoggerFactory.getLogger(ProductosImagenService.class);
	
	@Autowired
	private IProductosRepository productoRepository;
	
	@Autowired
	private IProductosImagenRepository productoImagenRepository;
	
	
	@Transactional
	@Override
	public SingleResponse<List<ProductosImagenEntity>> consultarProductosImgID(ProductosEntity producto) {
		List<ProductosImagenEntity> listaProductos = new ArrayList<>();
		
		try {
			listaProductos = productoImagenRepository.findByIdProducto(producto);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los Productos en la BD");
		}
		
		if(!listaProductos.isEmpty()) {
			SingleResponse<List<ProductosImagenEntity>> response = new SingleResponse<>();
			response.setOk(true);
			response.setMensaje("Se ha obtenido la lista de Productos exitosamente");
			response.setResponse(listaProductos);
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registros de Productos en la BD");
	}

	
	public SingleResponse<ProductosImagenEntity> guardarProductoImagen(RequestAltaProductoBean altaProductoImagen)
			throws BusinessException {
		SingleResponse<ProductosImagenEntity> r = new SingleResponse<ProductosImagenEntity>();

			// Guardado del producto en la BD  
			ProductosEntity productoGuardado = new ProductosEntity();
			
			Optional<ProductosEntity> productoOp = Optional.empty();		
			ProductosEntity producto = validarRequestAlta(altaProductoImagen);
			
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
				productoGuardado = productoRepository.save(producto);
			} catch (DataAccessException e) {
				log.error("Ha ocurrido un error inesperado. Exception {} {}", e.getMessage() + " " + e, e.getStackTrace());
				throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el producto en la base de datos");
			}

			// Guardado de imagens anexos (si existe)
			if (!altaProductoImagen.getImagenes().isEmpty()) {
				guardarImagenes(altaProductoImagen.getImagenes(), productoGuardado);
			}

			/**
			 * Se crea el objeto de respuesta con el folio y fecha de atención.
			 */
			ProductosImagenEntity respuestaAlta = new ProductosImagenEntity();
			respuestaAlta.setIdProducto(producto);
			
			/**
			 * Se crea la respuesta genérica de éxito de que no se presete algún error al
			 * crear el producto.
			 */
			r = new SingleResponse<>();
			r.setResponse(respuestaAlta);
			r.setMensaje("Se ha guardado el producto " + producto.getNombreProducto() +" exitosamente.");
			r.setOk(true);
			return r;
	}
	
	
	@Override
	public void guardarImagenes(List<ProductoImagen> imagenes, ProductosEntity producto) {
		List<ProductosImagenEntity> imagenesGuardadas = new ArrayList<>();
		imagenes.forEach(imagen -> {
			//Para cada imagen se agregan los datos a la entidad
			ProductosImagenEntity imagenProducto = new ProductosImagenEntity();
			imagenProducto.setNombreImagen(imagen.getNombreImagen());
			imagenProducto.setImagenBits(encode(imagen.getImagen()));
			imagenProducto.setTipoImagen(imagen.getTipoImagen());
			imagenProducto.setEstatus(imagen.getEstatus());
			imagenProducto.setImagenPredeterminado(1);
			imagenProducto.setIdProducto(producto);
		
			try {
				//Se guarda el imagen en la base de datos
				imagenesGuardadas.add(productoImagenRepository.save(imagenProducto));
			} catch (DataAccessException e) {
				log.error("Ha ocurrido un error inesperado. Exception {} {}",e.getMessage() + " " + e,
						e.getStackTrace());
				throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar los imagenes en la base de datos");
			}
		});
	
		
	}
	
	
	public ProductosEntity validarRequestAlta(RequestAltaProductoBean altaProductoImagen) {
		
		ProductosEntity producto = new ProductosEntity();
		producto.setCodigoReferencia(altaProductoImagen.getCodigoReferencia());
		producto.setNombreProducto(altaProductoImagen.getNombreProducto());
		producto.setDescripcionBreve(altaProductoImagen.getDescripcionBreve());
		producto.setLargoInterior(altaProductoImagen.getLargoInterior());
		producto.setLargoExterior(altaProductoImagen.getLargoExterior());
		producto.setAnchoInterior(altaProductoImagen.getAnchoInterior());
		producto.setAnchoExterior(altaProductoImagen.getAnchoExterior());
		producto.setAltoInterior(altaProductoImagen.getAltoInterior());
		producto.setAltoExterior(altaProductoImagen.getAltoExterior());
		//producto.setStock(altaProductoImagen.getStock());
		producto.setPrecioCompra(altaProductoImagen.getPrecioCompra());
		producto.setPrecioVenta(altaProductoImagen.getPrecioVenta());
		producto.setFechaAlta(altaProductoImagen.getFechaAlta());
		producto.setFechaModificacion(altaProductoImagen.getFechaModificacion());
		producto.setPeso(altaProductoImagen.getPeso());
		producto.setEstatus(altaProductoImagen.getEstatus());
	
		
	
		
		return producto;
		
	}
	
	public static byte[] encode(String value) {
		return java.util.Base64.getDecoder().decode(value);
	}


	@Override
	public void eliminaImagenes(ProductosImagenEntity producto) {
		try {
			productoImagenRepository.deleteById(producto.getIdImagen());
		} catch (EmptyResultDataAccessException e) {
			log.error("El registro no se encuentra en la base de datos {} {}",e.getMessage() + " " + e,
					e.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "El registro no se encuentra en la base de datos");
		}
		
	}
	
	@Override
	public SingleResponse<ProductosEntity> agregarImagen(RequestAltaProductoBean producto) {
		Optional<ProductosEntity> productoOp = Optional.empty();
		SingleResponse<ProductosEntity> r = new SingleResponse<ProductosEntity>();

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
		// Guardado de imagens anexos (si existe)
		if (!producto.getImagenes().isEmpty()) {
			guardarImagenes(producto.getImagenes(), productoOp.get());
		}
		
		ProductosEntity respuestaAlta = productoOp.get();
		
		/**
		 * Se crea la respuesta genérica de éxito de que no se presete algún error al
		 * crear el producto.
		 */
		r = new SingleResponse<>();
		r.setResponse(respuestaAlta);
		r.setOk(true);
		return r;
	}

	
	

}
