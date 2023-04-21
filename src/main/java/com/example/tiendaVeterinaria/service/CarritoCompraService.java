package com.example.tiendaVeterinaria.service;

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
import com.example.tiendaVeterinaria.model.dto.CarroComprasRequest;
import com.example.tiendaVeterinaria.model.dto.ResponseListarCarrito;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.CarroComprasEntity;
import com.example.tiendaVeterinaria.model.entity.ClienteEntity;
import com.example.tiendaVeterinaria.model.entity.ProductosEntity;
import com.example.tiendaVeterinaria.repository.ICarroComprasRepository;
import com.example.tiendaVeterinaria.repository.IClienteRepository;
import com.example.tiendaVeterinaria.repository.IProductoRepository;

@Service
public class CarritoCompraService implements ICarritoCompraService {
	
	
	private static final Logger log = LoggerFactory.getLogger(CarritoCompraService.class);
	
	@Autowired
	private ICarroComprasRepository carroComprasRepository;
	
	@Autowired
	private IProductoRepository productoRepository;
	
	@Autowired
	private IClienteRepository clienteRepository;

	
	@Transactional
	@Override
	public SingleResponse<CarroComprasEntity> agregarProducto(CarroComprasRequest request) {
		
		Optional<ProductosEntity> OProducto = Optional.empty();
		
		try {
			OProducto = productoRepository.findById(request.getIdProducto());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar el producto en la BD");
		}
		if(OProducto.isEmpty()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontró el producto con el Id " +request.getIdProducto() + "en la BD");
		}
		
		Optional<ClienteEntity> OCliente = Optional.empty();
		try {
			OCliente = clienteRepository.findById(request.getIdCliente());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar el cliente en la BD");
		}
		if(OCliente.isEmpty()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontró el cliente con el Id " +request.getIdCliente() + "en la BD");
		}
		
		Optional<CarroComprasEntity> OCarroCompra = Optional.empty();
		
		try {
			OCarroCompra = carroComprasRepository.findByClienteAndProducto(OCliente.get(), OProducto.get());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar el carrito de compras en la BD");
		}
		Integer cantidadTotal = request.getCantidad();
		CarroComprasEntity carrito = new CarroComprasEntity();
		if (!OCarroCompra.isEmpty()) {
			carrito = OCarroCompra.get();
			cantidadTotal = cantidadTotal + OCarroCompra.get().getCantidad();
		}
//		if (cantidadTotal > OProducto.get().getStock()) {
//			throw new BusinessException(HttpStatus.BAD_REQUEST, "La cantidad de productos("+cantidadTotal+")"
//					+ " excede el numero de productos en existencia("+OProducto.get().getStock()+").");
//		}
		carrito.setCantidad(cantidadTotal);
		carrito.setCliente(OCliente.get());
		carrito.setProducto(OProducto.get());
		
		try {
			carrito = carroComprasRepository.save(carrito);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al agregar el producto en el carrito de compras a la BD");
		}

		SingleResponse<CarroComprasEntity> response = new SingleResponse<>();
		response.setOk(true);
		response.setMensaje("Se agrego correctamente el producto en el carrito de compras");
		response.setResponse(carrito);
	
		return response;
	}
	
	@Transactional
	@Override
	public SingleResponse<ResponseListarCarrito> mostrarCarrito(Integer idCliente) {
		Optional<ClienteEntity> OCliente = Optional.empty();
		try {
			OCliente = clienteRepository.findById(idCliente);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar el cliente en la BD");
		}
		if(OCliente.isEmpty()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontró el cliente con el Id " +idCliente + "en la BD");
		}
		
		List<CarroComprasEntity> listado = new ArrayList<>();
		
		listado = listarCarrito(OCliente.get());
		SingleResponse<ResponseListarCarrito> response = new SingleResponse<>();
		response.setOk(true);
		if (listado.isEmpty()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Carrito vacío");
//			response.setMensaje("Carrito vacío");
		}else {
			double totalEstimado = 0.0d;
			for(CarroComprasEntity producto : listado) {
				totalEstimado += producto.getSubtotal();
			}
			ResponseListarCarrito carritoDto = new ResponseListarCarrito();
			carritoDto.setTotalEstimado(totalEstimado);
			carritoDto.setCarrito(listado);
			response.setMensaje("Carrito obtenido exitosamente");
			response.setResponse(carritoDto);
			log.info(""+totalEstimado);
		}
		return response;
	}
	
	
	@Transactional
	@Override
	public SingleResponse<ResponseListarCarrito> actualizarCantidad(CarroComprasRequest request) {
		Optional<ProductosEntity> OProducto = Optional.empty();
		
		try {
			OProducto = productoRepository.findById(request.getIdProducto());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar el producto en la BD");
		}
		if(OProducto.isEmpty()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontró el producto con el Id " +request.getIdProducto() + "en la BD");
		}
		
		Optional<ClienteEntity> OCliente = Optional.empty();
		try {
			OCliente = clienteRepository.findById(request.getIdCliente());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar el cliente en la BD");
		}
		if(OCliente.isEmpty()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontró el cliente con el Id " +request.getIdCliente() + "en la BD");
		}
		
		Optional<CarroComprasEntity> OCarroCompra = Optional.empty();
		
		try {
			OCarroCompra = carroComprasRepository.findByClienteAndProducto(OCliente.get(), OProducto.get());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar el carrito de compras en la BD");
		}
		
		if (OCarroCompra.isEmpty()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "No existe el producto en el carrito de compras.");
			
		}
		Integer cantidadTotal = request.getCantidad();
	
//		if (cantidadTotal > OProducto.get().getStock()) {
//			throw new BusinessException(HttpStatus.BAD_REQUEST, "La cantidad de productos("+cantidadTotal+")"
//					+ " excede el número de productos en existencia("+OProducto.get().getStock()+").");
//		}
		CarroComprasEntity carrito = new CarroComprasEntity();
		carrito = OCarroCompra.get();
		
		carrito.setCantidad(cantidadTotal);

		
		try {
			carrito = carroComprasRepository.save(carrito);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al agregar el producto en el carrito de compras a la BD");
		}

		List<CarroComprasEntity> listado = new ArrayList<>();
		
		listado = listarCarrito(OCliente.get());
		SingleResponse<ResponseListarCarrito> response = new SingleResponse<>();
		response.setOk(true);
		if (listado.isEmpty()) {
			
			response.setMensaje("Carrito vacío");
		}else {
			double totalEstimado = 0.0d;
			for(CarroComprasEntity producto : listado) {
				totalEstimado += producto.getSubtotal();
			}
			ResponseListarCarrito carritoDto = new ResponseListarCarrito();
			carritoDto.setTotalEstimado(totalEstimado);
			carritoDto.setCarrito(listado);
			response.setMensaje("Carrito obtenido exitosamente");
			response.setResponse(carritoDto);
			log.info(""+totalEstimado);
		}
		return response;
	}

	
	@Transactional
	@Override
	public SingleResponse<ResponseListarCarrito> eliminarProducto(Integer idCarrito) {
		Optional<CarroComprasEntity> OCarroCompra = Optional.empty();
		
		try {
			OCarroCompra = carroComprasRepository.findById(idCarrito);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar el carrito de compras en la BD");
		}
		
		if (OCarroCompra.isEmpty()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "No existe el producto en el carrito de compras.");
			
		}
		
		try {
			carroComprasRepository.deleteById(idCarrito);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el producto del carrito de compras en la BD");
		}
		List<CarroComprasEntity> listado = new ArrayList<>();
		
		listado = listarCarrito(OCarroCompra.get().getCliente());
		SingleResponse<ResponseListarCarrito> response = new SingleResponse<>();
		response.setOk(true);
		if (listado.isEmpty()) {
			
			response.setMensaje("Carrito vacío");
		}else {
			double totalEstimado = 0.0d;
			for(CarroComprasEntity producto : listado) {
				totalEstimado += producto.getSubtotal();
			}
			ResponseListarCarrito carritoDto = new ResponseListarCarrito();
			carritoDto.setTotalEstimado(totalEstimado);
			carritoDto.setCarrito(listado);
			response.setMensaje("Carrito obtenido exitosamente");
			response.setResponse(carritoDto);
			log.info(""+totalEstimado);
		}
		return response;
	}

	
	private List<CarroComprasEntity> listarCarrito(ClienteEntity cliente){
		List<CarroComprasEntity> carritoCliente = new ArrayList<>();
		try {
			carritoCliente = carroComprasRepository.findByCliente(cliente);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar el carrito de compras en la BD");
		}

		return carritoCliente;
	}






}
