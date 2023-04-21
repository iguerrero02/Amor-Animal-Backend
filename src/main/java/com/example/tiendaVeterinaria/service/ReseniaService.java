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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiendaVeterinaria.exceptions.BusinessException;
import com.example.tiendaVeterinaria.model.dto.ReseniaOrdenDTO;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ClienteEntity;
import com.example.tiendaVeterinaria.model.entity.OrdenDetalleEntity;
import com.example.tiendaVeterinaria.model.entity.OrdenesEntity;
import com.example.tiendaVeterinaria.model.entity.ProductosEntity;
import com.example.tiendaVeterinaria.model.entity.ReseniaEntity;
import com.example.tiendaVeterinaria.repository.IClienteRepository;
import com.example.tiendaVeterinaria.repository.IOrdenRepository;
import com.example.tiendaVeterinaria.repository.IProductoRepository;
import com.example.tiendaVeterinaria.repository.IReseniaRepository;

@Service
public class ReseniaService implements IReseniaService {

	@Autowired
	IReseniaRepository reseniaRepository;

	@Autowired
	IProductoRepository productoRepository;

	@Autowired
	IClienteRepository clienteRepository;
	
	@Autowired
	IOrdenRepository ordenRepository;

	private static final Logger log = LoggerFactory.getLogger(ReseniaService.class);

	public static final Integer ESTATUS_ACTIVO = 1;

	@Override
	@Transactional
	public SingleResponse<ReseniaEntity> crearResenia(ReseniaOrdenDTO resenia) {

		Optional<ReseniaEntity> oResenia = Optional.empty();
		Optional<ClienteEntity> oCliente = Optional.empty();
		Optional<ProductosEntity> oProducto = Optional.empty();
		Optional<OrdenesEntity> oOrdenEntity = Optional.empty();
		List<OrdenesEntity> oOrden = new ArrayList<OrdenesEntity>();
		
		
		try {
			oCliente = clienteRepository.findById(resenia.getIdCliente());
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los usuarios en la BD");
		}
		
		if(oCliente.isEmpty()) {
			throw new BusinessException(HttpStatus.NOT_FOUND, "Error al encontrar el cliente en la BD");
		}
				
		try {
			oCliente = clienteRepository.findByCorreoElectronicoIgnoreCaseAndEstatus(
					oCliente.get().getCorreoElectronico(), ESTATUS_ACTIVO);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los usuarios en la BD");
		}
		
		ClienteEntity cliente = oCliente.get();
		
//		try {
//			oOrdenEntity = ordenRepository.findById(resenia.getIdOrden());
//		} catch (DataAccessException ex) {
//			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
//					ex.getStackTrace());
//			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los Productos en la BD");
//		}				
		
//		try {
//			oOrden = ordenRepository.findByClienteEstatusOrden(cliente.getIdCliente());
//		} catch (DataAccessException excepcion) {
//			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + excepcion,
//					excepcion.getStackTrace());
//			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar la orden en la BD");
//		}
		
		if(oOrden.isEmpty()) {
			throw new BusinessException(HttpStatus.NOT_FOUND, "No hay ordenes entregados no puedes dar una reseña");
		}
		
		
		List<Integer> listaIdProductos = new ArrayList<Integer>();
		for (OrdenesEntity ordenesEntity : oOrden) {
			for (OrdenDetalleEntity ordenesDetalle : ordenesEntity.getOrdenDetalle()) {
				listaIdProductos.add(ordenesDetalle.getProducto().getIdProducto());
			}
		}
		
		for (Integer integer : listaIdProductos) {
			log.info("idProductos" + integer);
		}
		
		if(!listaIdProductos.contains(resenia.getIdProducto())) {
			throw new BusinessException(HttpStatus.UNAUTHORIZED, "No puedes dar una reseña ya que no tienes una orden con ese producto");
		}
		
		
		
		try {
			oProducto = productoRepository.findById(resenia.getIdProducto());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los Productos en la BD");
		}
		
		if(oProducto.isEmpty()) {
			throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontro el producto");
		}
						

		try {
			oResenia = reseniaRepository.findByClienteProducto(oCliente.get().getIdCliente(),
					oProducto.get().getIdProducto());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar las Reseñas en la BD");
		}

		if (oResenia.isPresent()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST,
					"Ya existe una reseña para el " + oProducto.get().getNombreProducto());
		}

		ReseniaEntity reseniaNueva = new ReseniaEntity();
		
		reseniaNueva.setEncabezado(resenia.getEncabezado());
		reseniaNueva.setComentarios(resenia.getComentarios());
		reseniaNueva.setFecha(LocalDateTime.now());
		reseniaNueva.setValoracion(resenia.getValoracion());
		reseniaNueva.setCliente(cliente);
		reseniaNueva.setProducto(oProducto.get());

		try {
			reseniaNueva = reseniaRepository.save(reseniaNueva);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar la resenia en la BD");
		}
		SingleResponse<ReseniaEntity> response = new SingleResponse<>();
		response.setOk(true);
		response.setMensaje("Se creo la reseña con el producto " + oProducto.get().getNombreProducto() + " exitosamente.");
		response.setResponse(reseniaNueva);
		return response;
	}

	@Override
	@Transactional
	public SingleResponse<List<ReseniaEntity>> consultarResenia() {
		List<ReseniaEntity> listaResenias = new ArrayList<>();

		try {
			listaResenias = reseniaRepository.findAll();
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los usuarios en la BD");
		}

		if (!listaResenias.isEmpty()) {
			SingleResponse<List<ReseniaEntity>> response = new SingleResponse<>();
			response.setMensaje("Se ha obtenido la lista de reseñas exitosamente");
			response.setOk(true);
			response.setResponse(listaResenias);
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registros de usuarios en la BD");
	}

	
	@Override
	public SingleResponse<ReseniaEntity> consultarReseniaClienteProducto(Integer idCliente,Integer idProducto) {
		Optional<ReseniaEntity> listaReseniasCliente = Optional.empty();

		try {
			listaReseniasCliente = reseniaRepository.findByClienteProducto(idCliente,idProducto);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los usuarios en la BD");
		}

		if (!listaReseniasCliente.isEmpty()) {
			SingleResponse<ReseniaEntity> response = new SingleResponse<ReseniaEntity>();
			response.setMensaje("Se ha obtenido la lista de reseñas del cliente exitosamente");
			response.setOk(true);
			response.setResponse(listaReseniasCliente.get());
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron reseñas con el cliente en la BD");
	}

	

	@Override
	@Transactional
	public SingleResponse<ReseniaEntity> consultarReseniaDetalle(Integer idResenia) {
		Optional<ReseniaEntity> oResenia = Optional.empty();

		try {
			oResenia = reseniaRepository.findById(idResenia);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los usuarios en la BD");
		}

		if (!oResenia.isEmpty()) {
			SingleResponse<ReseniaEntity> response = new SingleResponse<>();
			response.setMensaje("Se a optenido la reseña exitosamente");
			response.setOk(true);
			response.setResponse(oResenia.get());
			return response;
		}

		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registros de usuarios en la BD");
	}

	@Override
	@Transactional
	public SingleResponse<ReseniaEntity> actualizarResenia(ReseniaEntity resenia) {
		Optional<ReseniaEntity> oResenia = Optional.empty();

		try {
			oResenia = reseniaRepository.findById(resenia.getIdResenia());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los usuarios en la BD");
		}

		if (oResenia.isEmpty()) {
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "No existe el ID de la reseña en la BD");
		}

		ReseniaEntity reseniaDb = oResenia.get();

		if (resenia.getComentarios() != null) {
			reseniaDb.setComentarios(resenia.getComentarios());
		}

		if (resenia.getEncabezado() != null) {
			reseniaDb.setEncabezado(resenia.getEncabezado());
		}

		if (resenia.getFecha() != null) {
			reseniaDb.setFecha(resenia.getFecha());
		}

		if (resenia.getFecha() != null) {
			reseniaDb.setFecha(resenia.getFecha());
		}

		if (resenia.getValoracion() != null) {
			reseniaDb.setValoracion(resenia.getValoracion());
		}

		try {
			reseniaRepository.save(reseniaDb);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los usuarios en la BD");
		}

		SingleResponse<ReseniaEntity> response = new SingleResponse<>();
		response.setMensaje("Se a actualizado la rezeña correctamente");
		response.setOk(true);
		response.setResponse(reseniaDb);
		return response;
	}

	@Override
	@Transactional
	public SingleResponse<ReseniaEntity> borrarResenia(Integer idResenia) {
		Optional<ReseniaEntity> oResenia = Optional.empty();

		try {
			oResenia = reseniaRepository.findById(idResenia);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los usuarios en la BD");
		}

		if (oResenia.isEmpty()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraro la reseñia en la BD");
		}

		try {
			reseniaRepository.deleteById(idResenia);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al borrar el usuario en la BD");
		}

		SingleResponse<ReseniaEntity> response = new SingleResponse<>();
		response.setMensaje("Se a eliminado la resenia correctamente");
		response.setOk(true);
		return response;
	}

	@Override
	public SingleResponse<List<ReseniaEntity>> consultarReseniaCliente(Integer idCliente) {
		List<ReseniaEntity> listaReseniasCliente = new ArrayList<ReseniaEntity>();
		
		try {		
			listaReseniasCliente = reseniaRepository.findByCliente(idCliente);			
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontraron resenias con ese id cliente");
		}
		
		SingleResponse<List<ReseniaEntity>> response = new SingleResponse<List<ReseniaEntity>>();		
		
		if(!listaReseniasCliente.isEmpty()) {
			response.setMensaje("Las resenias por cliente se consultaron correctamente");
			response.setOk(true);
			response.setResponse(listaReseniasCliente);
			return response;
		}
		throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontraron resenias de ningun producto");		
		
	}
}
