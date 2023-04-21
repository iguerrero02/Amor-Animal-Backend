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

import com.example.tiendaVeterinaria.exceptions.BusinessException;
import com.example.tiendaVeterinaria.model.dto.ListaDeseosProductosDTO;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ClienteEntity;
import com.example.tiendaVeterinaria.model.entity.ListaDeseosEntity;
import com.example.tiendaVeterinaria.model.entity.ProductosEntity;
import com.example.tiendaVeterinaria.repository.IClienteRepository;
import com.example.tiendaVeterinaria.repository.IListaDeseosRepository;
import com.example.tiendaVeterinaria.repository.IProductoRepository;

@Service
public class ListaDeseosService implements IListaDeseosService {

	private static final Logger log = LoggerFactory.getLogger(CategoriasService.class);
	
	@Autowired
	private IListaDeseosRepository listaDeseosRepository;
	
	@Autowired
	private IClienteRepository clienteRepository;
	
	@Autowired
	private IProductoRepository productoRepository;
	
	
	@Override
	public SingleResponse<List<ListaDeseosProductosDTO>> consultarTodos(Integer idCliente) {
		List<ListaDeseosEntity> listaDeseos = new ArrayList<ListaDeseosEntity>();
		Optional<ClienteEntity> clienteOpcional = Optional.empty();
		
		try {
			clienteOpcional = clienteRepository.findById(idCliente);
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + 
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,"Erro al consultar las listas de deseos");
		}
		log.info("clienteOpcional"+clienteOpcional);
		if(clienteOpcional.get() == null) {
			throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontro el cliente");
		}	
		
		try {
			listaDeseos = listaDeseosRepository.findByCliente(clienteOpcional.get());
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {}");
			excepcion.getStackTrace();
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,"Erro al consultar las listas de deseos");
		}		
		if(listaDeseos == null) {
			throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontro una lista de deseos");
		}
						
		List<ListaDeseosProductosDTO> listaDeseosProductosDTO = new ArrayList<ListaDeseosProductosDTO> ();
		
		for (ListaDeseosEntity listaDeseosEntity : listaDeseos) {
			ListaDeseosProductosDTO objetoDeseosProducto = new ListaDeseosProductosDTO();
				objetoDeseosProducto.setFecha(listaDeseosEntity.getFecha());
				objetoDeseosProducto.setIdListaDeseo(listaDeseosEntity.getIdListaDeseo());
				objetoDeseosProducto.setProducto(listaDeseosEntity.getProducto());
				listaDeseosProductosDTO.add(objetoDeseosProducto);									
		}		
										
		 SingleResponse<List<ListaDeseosProductosDTO>> response = new SingleResponse<List<ListaDeseosProductosDTO>>();
		
		if(!listaDeseosProductosDTO.isEmpty()) {
			response.setMensaje("Se obtuvo la lista de deseos con exito");
			response.setOk(true);
			response.setResponse(listaDeseosProductosDTO);
			return response;
		}		
		throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontro una lista de deseos");

	}

	@Override
	public SingleResponse<ListaDeseosEntity> guardarListaDeseo(Integer idCliente, Integer idProducto) {

		Optional<ClienteEntity> opcionalCliente = Optional.empty();
		Optional<ProductosEntity> opcionalProducto = Optional.empty();
		
		try {			
			
			opcionalCliente = clienteRepository.findById(idCliente);			
		
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + 
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "No se encontro el cliente");
		}
		try {
			opcionalProducto = productoRepository.findById(idProducto);			
		} catch (DataAccessException excepcion) {
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "No se encontro el producto");
		}
		LocalDateTime fechaHoraActual = LocalDateTime.now();
		
		ListaDeseosEntity deseoEntity = new ListaDeseosEntity();
		deseoEntity.setCliente(opcionalCliente.get());
		deseoEntity.setProducto(opcionalProducto.get());		
		deseoEntity.setFecha(fechaHoraActual);		
		
		try {
			listaDeseosRepository.save(deseoEntity);
		} catch (DataAccessException excepcion) {
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "no se pudo agregar el producto a la lista de deseos");
		}
		
		SingleResponse<ListaDeseosEntity> response = new SingleResponse<ListaDeseosEntity>();
		
		response.setMensaje("Se agrego el producto a la lista de deseos");
		response.setOk(true);
		response.setResponse(deseoEntity);
		return response;
	}

	@Override
	public SingleResponse<ListaDeseosEntity> eliminarListaDeseo(Integer idListaDeseo) {

		Optional<ListaDeseosEntity> opcionLista = Optional.empty();
		
		try {			
			opcionLista = listaDeseosRepository.findById(idListaDeseo);					
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + 
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "No se encontro el deseo");
		}
		
		try {
			listaDeseosRepository.deleteById(idListaDeseo);
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + 
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "No se puedo eliminar la lista de deseo");					
		}
		
		SingleResponse<ListaDeseosEntity> response = new SingleResponse<ListaDeseosEntity>();
		
		response.setMensaje("Se elimino correctamente");
		response.setOk(true);
		response.setResponse(opcionLista.get());
	
		return response;
	}
	
	
}
