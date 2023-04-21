package com.example.tiendaVeterinaria.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.tiendaVeterinaria.exceptions.BusinessException;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.OfertaEntity;
import com.example.tiendaVeterinaria.model.entity.ProductosEntity;
import com.example.tiendaVeterinaria.repository.IOfertaRepository;
import com.example.tiendaVeterinaria.repository.IProductoRepository;

@Service
public class OfertaService implements IOfertaService{
	
	private static final Logger log = LoggerFactory.getLogger(OfertaService.class);
		
	@Autowired
	private IOfertaRepository ofertaRepository;
	
	@Autowired
	private IProductoRepository productoRepository;

	@Override
	public SingleResponse<List<OfertaEntity>> consultarTodosOferta() {
		List<OfertaEntity> listaOfertas = new ArrayList<OfertaEntity>();
		try {
			listaOfertas = ofertaRepository.findAll();
		} catch (DataAccessException excepcion) {		
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " ,
					excepcion.getStackTrace());		
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar las ofertas en la base de datos");
		}
		SingleResponse<List<OfertaEntity>> response = new SingleResponse<List<OfertaEntity>>();
		
		
		if(!listaOfertas.isEmpty()) {
			 List<OfertaEntity> nuevaListaOferta = obtenerPrecioOferta(listaOfertas);
			response.setResponse(nuevaListaOferta);
			response.setOk(true);
			response.setResponse(listaOfertas);
			return response;

		}
		
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron ofertas en la Base de datos");		
	}
	
	private List<OfertaEntity> obtenerPrecioOferta(List<OfertaEntity> lista) {
		List<OfertaEntity> nuevoListaOferta = new ArrayList<OfertaEntity>();
		for (OfertaEntity ofertaEntity : lista) {
			Integer descuentoPorcentaje = ofertaEntity.getDescuentoEnPorcentaje();
			Double precioVenta = ofertaEntity.getProducto().getPrecioVenta();
			Double precioConOferta = precioVenta - (precioVenta * descuentoPorcentaje / 100);
			ofertaEntity.setPrecioConOferta(precioConOferta);
			nuevoListaOferta.add(ofertaEntity);			
		}
		return nuevoListaOferta;
		
	}
	private Double obtenerPrecioOfertaGuardar(Integer descuentoPorcentaje,Double precioVenta ) {				
		Double precioConOferta = precioVenta - (precioVenta * descuentoPorcentaje / 100);		
		return precioConOferta;
	}

	@Override
	public SingleResponse<OfertaEntity> guardarOferta(OfertaEntity oferta, Integer idProducto) {
		Optional<ProductosEntity> opcionalProducto = Optional.empty();
		
		try {
			opcionalProducto = productoRepository.findById(idProducto);
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {} " ,excepcion.getMessage()
					+ excepcion.getStackTrace());
 			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar el producto en la BD");

		}
		
		if(opcionalProducto.isEmpty()) {
 			throw new BusinessException(HttpStatus.NOT_FOUND, "El producto no existe");
		}
				
		ProductosEntity nuevoProducto = opcionalProducto.get();
		Double precioConOferta = obtenerPrecioOfertaGuardar(oferta.getDescuentoEnPorcentaje(),nuevoProducto.getPrecioVenta());
		nuevoProducto.setPrecioOferta(precioConOferta);
		oferta.setProducto(nuevoProducto);
		oferta.setFechaFin(LocalDateTime.now());
		oferta.setFechaInicio(LocalDateTime.now());

		try {			
			oferta = ofertaRepository.save(oferta);		
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {} " ,excepcion.getMessage()
					+ excepcion.getStackTrace());
 			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar la oferta en la BD");
		}
		
		SingleResponse<OfertaEntity> response = new SingleResponse<OfertaEntity>();
		response.setMensaje("La oferta se agrego correctamente");
		response.setOk(true);
		response.setResponse(oferta);		
		return response;
	}

	@Override
	public SingleResponse<OfertaEntity> actualizarOferta(OfertaEntity oferta, Integer idProducto) {
		Optional<ProductosEntity> opcionalProducto = Optional.empty();
		Optional<OfertaEntity> opcionalOferta =  Optional.empty();
		
		try {
			opcionalOferta = ofertaRepository.findById(oferta.getIdOferta());
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {} " ,excepcion.getMessage()
					+ excepcion.getStackTrace());
 			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar el producto en la BD");

		}
		
		if(opcionalOferta.isEmpty()) {
 			throw new BusinessException(HttpStatus.NOT_FOUND, "La oferta no existe");
		}
		
		try {
			opcionalProducto = productoRepository.findById(idProducto);
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {} " ,excepcion.getMessage()
					+ excepcion.getStackTrace());
 			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar el producto en la BD");

		}
		
		if(opcionalProducto.isEmpty()) {
 			throw new BusinessException(HttpStatus.NOT_FOUND, "El producto no existe");
		}
				
		OfertaEntity ofertaNueva = opcionalOferta.get();
	
		ProductosEntity nuevoProducto = opcionalProducto.get();
		Double precioConOferta = obtenerPrecioOfertaGuardar(oferta.getDescuentoEnPorcentaje(),nuevoProducto.getPrecioVenta());
		nuevoProducto.setPrecioOferta(precioConOferta);
		ofertaNueva.setProducto(nuevoProducto);
		ofertaNueva.setCodigoOferta(oferta.getCodigoOferta());
		ofertaNueva.setCondicionesOferta(oferta.getCondicionesOferta());
		ofertaNueva.setDescripcion(oferta.getDescripcion());
		ofertaNueva.setDescuentoEnPorcentaje(oferta.getDescuentoEnPorcentaje());
		ofertaNueva.setEstatus(oferta.getEstatus());
		ofertaNueva.setFechaFin(oferta.getFechaFin());
		ofertaNueva.setFechaInicio(oferta.getFechaInicio());
		ofertaNueva.setNumeroUso(oferta.getNumeroUso());
		ofertaNueva.setTipoOferta(oferta.getTipoOferta());		

		try {			
			ofertaNueva = ofertaRepository.save(ofertaNueva);		
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {} " ,excepcion.getMessage()
					+ excepcion.getStackTrace());
 			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar la oferta en la BD");
		}
		
		SingleResponse<OfertaEntity> response = new SingleResponse<OfertaEntity>();
		response.setMensaje("La oferta se actualizo correctamente");
		response.setOk(true);
		response.setResponse(ofertaNueva);
		return response;
	}

	@Override
	public SingleResponse<List<OfertaEntity>> consultarTodosActivos() {

		List<OfertaEntity> listaTodosActivos = new ArrayList<OfertaEntity>();
		try {
			listaTodosActivos = ofertaRepository.findByEstatus(1);			
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {} " ,excepcion.getMessage()
					+ excepcion.getStackTrace());
 			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar la oferta en la BD");
 		}
		
		SingleResponse<List<OfertaEntity>> response = new SingleResponse<List<OfertaEntity>> ();
		if(!listaTodosActivos.isEmpty()) {
			response.setOk(true);
			response.setMensaje("La ofertas se consultaron correctamente");
			response.setResponse(listaTodosActivos);	
			return response;

		}
		
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron ofertas activas en la Base de datos");

	}

	@Override
	public SingleResponse<OfertaEntity> actualizarOfertaEstatus(Integer idOferta,Integer estatus) {
		Optional<OfertaEntity> opcionalOferta = Optional.empty();		
		try {
			opcionalOferta = ofertaRepository.findById(idOferta);
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {} " ,excepcion.getMessage()
					+ excepcion.getStackTrace());
 			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al econtrar la oferta en la BD");
 		}
		
		if(opcionalOferta.isEmpty()) {
 			throw new BusinessException(HttpStatus.NOT_FOUND, "La oferta no existe");
		}
		
		OfertaEntity ofertaNueva = opcionalOferta.get();
		ofertaNueva.setEstatus(estatus);

		try {		
			ofertaNueva = ofertaRepository.save(ofertaNueva);
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {} " ,excepcion.getMessage()
					+ excepcion.getStackTrace());
 			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar la oferta en la BD");		}
		
		SingleResponse<OfertaEntity> response = new SingleResponse<OfertaEntity>();
		
		response.setMensaje("El estatus de la oferta se actualizo correctamente");
		response.setOk(true);
		response.setResponse(ofertaNueva);
		return response;
	}

	@Override
    @Scheduled(fixedDelay = 43200000) // se ejecuta cada hora
	public void actualizarOfertasVencidas() {
		List<OfertaEntity> listaOfertas = new ArrayList<OfertaEntity>();		
		
		try {
			listaOfertas = ofertaRepository.findByFechaFinBeforeAndEstatusNot(LocalDateTime.now(),0);			
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {} " ,excepcion.getMessage()
					+ excepcion.getStackTrace());
 			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar las ofertas en la BD");		
 			
		}
		if(!listaOfertas.isEmpty()) {
			for (OfertaEntity ofertaEntity : listaOfertas) {
				ProductosEntity productoNuevo = ofertaEntity.getProducto();
				productoNuevo.setPrecioOferta(null);
				ofertaEntity.setEstatus(0);
				productoRepository.save(productoNuevo);
				ofertaRepository.save(ofertaEntity);
			}
		}		
			
	}

	@Override
	public SingleResponse<OfertaEntity> busquedaPorIdProducto(Integer idProducto) {
		Optional<OfertaEntity> ofertaOpcional = Optional.empty();
				
		try {
			
			ofertaOpcional = ofertaRepository.findByProductoAndIdProducto(idProducto);
			
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepción {} {}", excepcion.getMessage() + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro al buscar por el id del producto");
		}
		if(ofertaOpcional.isEmpty()) {
			throw new BusinessException(HttpStatus.NOT_FOUND, "La oferta con el id producto no se encontro");
		}
		
		SingleResponse<OfertaEntity> response = new SingleResponse<OfertaEntity>();
		response.setMensaje("La busqueda se realizó correctamente");
		response.setResponse(ofertaOpcional.get());
		response.setOk(true);
		return response;
	}

	
}
