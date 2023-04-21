package com.example.tiendaVeterinaria.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectReader;
import com.example.tiendaVeterinaria.exceptions.BusinessException;
import com.example.tiendaVeterinaria.model.dto.CrearOrdenRequest;
import com.example.tiendaVeterinaria.model.dto.EstatusOrden;
import com.example.tiendaVeterinaria.model.dto.OrdenActualizarDTO;
import com.example.tiendaVeterinaria.model.dto.OrdenDetalleAgregarProductoDTO;
import com.example.tiendaVeterinaria.model.dto.OrdenDto;
import com.example.tiendaVeterinaria.model.dto.ResponseListarCarrito;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ClienteEntity;
import com.example.tiendaVeterinaria.model.entity.DireccionEntity;
import com.example.tiendaVeterinaria.model.entity.OrdenDetalleEntity;
import com.example.tiendaVeterinaria.model.entity.OrdenesEntity;
import com.example.tiendaVeterinaria.model.entity.ProductosEntity;
import com.example.tiendaVeterinaria.repository.ICarroComprasRepository;
import com.example.tiendaVeterinaria.repository.IClienteRepository;
import com.example.tiendaVeterinaria.repository.IOrdenDetalleRepository;
import com.example.tiendaVeterinaria.repository.IOrdenRepository;
import com.example.tiendaVeterinaria.repository.IProductoRepository;

@Service
public class OrdenesService implements IOrdenesService{
	
	
	private static final Logger log = LoggerFactory.getLogger(OrdenesService.class);
	
	@Autowired
	private ICarritoCompraService carritoCompraService;
	
	@Autowired
	private IDireccionesServices direccionesServices;
	
	@Autowired
	private ICarroComprasRepository carroComprasRepository;
	
	@Autowired
	private IProductoRepository productoRepository;
	
	@Autowired
	private IOrdenRepository ordenRepository;
	
	@Autowired
	private IOrdenDetalleRepository ordenDetalleRepository;

	@Autowired
	private IClienteRepository clienteRepository;

	@Transactional
	@Override
	public SingleResponse<Page<OrdenesEntity>> consultarPorPaginas(int noPagina, String campo, String direccion,
			String buscar) {
		int pageSize = 10;
		Pageable pageable = PageRequest.of(noPagina - 1, pageSize,
				direccion.equalsIgnoreCase("asc") ? Sort.by(campo).ascending() : Sort.by(campo).descending());

		Page<OrdenesEntity> ordenPage = Page.empty();

		try {
			if (buscar != null) {
				ordenPage = ordenRepository.findAll(buscar, pageable);
			} else {
				ordenPage = ordenRepository.findAll(pageable);
			}

		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar las órdenes en la BD");
		}

		if (!ordenPage.isEmpty()) {
			SingleResponse<Page<OrdenesEntity>> response = new SingleResponse<>();
			response.setOk(true);
			response.setMensaje("Se ha obtenido la lista de órdenes exitosamente");
			response.setResponse(ordenPage);
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registros en la página " + noPagina);
	}


	@Transactional
	@Override
	public SingleResponse<OrdenDto> detalleOrden(Integer idOrden) {
		Optional<OrdenesEntity> oOrden = Optional.empty();
		
		try {
			oOrden = ordenRepository.findById(idOrden);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar las órdenes en la BD");
		}
		
		if(oOrden.isEmpty()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Orden con id " + idOrden + " no fue encontrada en la BD");
		}
		SingleResponse<OrdenDto> response = new SingleResponse<>();
		OrdenesEntity orden = oOrden.get();
		OrdenDto ordenDto = new OrdenDto();
		ordenDto.setCliente(orden.getCliente());
		ordenDto.setDetalles(orden.getOrdenDetalle());
		ordenDto.setCalle(orden.getCalle());
		ordenDto.setCiudad(orden.getCiudad());
		ordenDto.setCodigoPostal(orden.getCodigoPostal());
		ordenDto.setColonia(orden.getColonia());
		ordenDto.setEntreCalle1(orden.getEntreCalle1());
		ordenDto.setEntreCalle2(orden.getEntreCalle2());
		ordenDto.setEstatusOrden(orden.getEstatusOrden());
		ordenDto.setFechaOrden(orden.getFechaOrden());
		ordenDto.setIdOrden(idOrden);
		ordenDto.setImpuesto(orden.getImpuesto());
		ordenDto.setMetodoPago(orden.getMetodoPago());
		ordenDto.setNumeroExterior(orden.getNumeroExterior());
		ordenDto.setNumeroInterior(orden.getNumeroInterior());
		ordenDto.setSubTotal(orden.getSubTotal());
		ordenDto.setTelefono(orden.getTelefono());
		ordenDto.setTotal(orden.getTotal());
//		orden.setOrdenes(orden.getOrdenes());
		response.setOk(true);
		response.setMensaje("Se obtuvo el detalle de la orden exitosamente");
		response.setResponse(ordenDto);
		return response;
	}


	@Transactional
	@Override
	public SingleResponse<OrdenDto> crearOrden(CrearOrdenRequest request) {
		DireccionEntity direccionCompra = direccionesServices.consultarDireccionePorId(request.getIdDireccion()).getResponse();
		ResponseListarCarrito carrito = carritoCompraService.mostrarCarrito(request.getIdCliente()).getResponse();
		ClienteEntity cliente = carrito.getCarrito().get(0).getCliente();
		OrdenesEntity nuevaOrden = new OrdenesEntity();
		nuevaOrden.setCalle(direccionCompra.getCalle());
		nuevaOrden.setCiudad(direccionCompra.getCiudad());
		nuevaOrden.setCliente(cliente);
		nuevaOrden.setCodigoPostal(direccionCompra.getCodigoPostal());
		nuevaOrden.setColonia(direccionCompra.getColonia());
		nuevaOrden.setEntreCalle1(direccionCompra.getEntreCalle1());
		nuevaOrden.setEntreCalle2(direccionCompra.getEntreCalle2());
		nuevaOrden.setEstatusOrden(EstatusOrden.NUEVO);
		nuevaOrden.setFechaOrden(LocalDateTime.now());
		nuevaOrden.setImpuesto(request.getIva());
		nuevaOrden.setMetodoPago(request.getMetodoPago());
		nuevaOrden.setNumeroExterior(direccionCompra.getNumeroExterior());
		nuevaOrden.setNumeroInterior(direccionCompra.getNumeroInterior());
		nuevaOrden.setSubTotal(request.getTotalProductos());
		nuevaOrden.setTelefono(direccionCompra.getTelefono());
		nuevaOrden.setTotal(request.getPagoTotal());
		
		try {
			nuevaOrden = ordenRepository.save(nuevaOrden);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar la orden en la BD");
		}
		OrdenesEntity ordenGuardada = nuevaOrden;
		Set<OrdenDetalleEntity> detallesOrden = new HashSet<>();
		carrito.getCarrito().forEach(carroProducto->{
			OrdenDetalleEntity ordenDetalle = new OrdenDetalleEntity();
			ordenDetalle.setCantidad(carroProducto.getCantidad());
			ordenDetalle.setOrden(ordenGuardada);
			if(carroProducto.getProducto().getPrecioOferta() != null) {
				ordenDetalle.setPrecio(carroProducto.getProducto().getPrecioOferta());
			}else {
				ordenDetalle.setPrecio(carroProducto.getProducto().getPrecioVenta());
			}
			ordenDetalle.setProducto(carroProducto.getProducto());
			ordenDetalle.setSubtotal(carroProducto.getSubtotal());
			try {
				detallesOrden.add(ordenDetalleRepository.save(ordenDetalle));
				
			} catch (DataAccessException ex) {
				log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
						ex.getStackTrace());
				throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el datelle de la orden en la BD");
			}
		});
		ordenGuardada.setOrdenDetalle(detallesOrden);
		
		try {
			carroComprasRepository.deleteByCliente(ordenGuardada.getCliente().getIdCliente());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al borrar el carrito de compras en la BD");
		}
		
		return detalleOrden(ordenGuardada.getIdOrden());
	}


//	@Override
//	public SingleResponse<OrdenDetalleEntity> agregarProductoOrden(Integer idOrdenDetalle, ProductosEntity producto) {
//
//		Optional<OrdenDetalleEntity> ordenOpcional = Optional.empty();
//		
//		try {
//			ordenOpcional = ordenDetalleRepository.findById(idOrdenDetalle);
//		} catch (DataAccessException excepcion) {
//			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + excepcion,
//					excepcion.getStackTrace());
//			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar por el id de la ordern detalle en la BD");
//		}
//		OrdenDetalleEntity ordenDetalle = ordenOpcional.get()
//		
//		
//		return null;
//	}


	@Override
	public SingleResponse<OrdenesEntity> eliminarOrden(Integer idOrden) {
		Optional<OrdenesEntity> opcionalOrden = Optional.empty();
		
		try {
			opcionalOrden = ordenRepository.findById(idOrden);
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el id de la orden en la BD");
		}
		
		OrdenesEntity orden = opcionalOrden.get();
		
		for (OrdenDetalleEntity data : orden.getOrdenDetalle()) {
			try {
				ordenDetalleRepository.deleteById(data.getIdOrdenDetalle());
			} catch (DataAccessException excepcion) {
				log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + excepcion,
						excepcion.getStackTrace());
				throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el id de la orden en la BD");
			}
		}
						
		
		
		
		try {
			ordenRepository.deleteById(opcionalOrden.get().getIdOrden());
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar de la orden de BD");
		}
		
		SingleResponse<OrdenesEntity> response = new SingleResponse<OrdenesEntity>();
		response.setMensaje("Se elimino la orden correctamente");
		response.setOk(true);
		response.setResponse(opcionalOrden.get());		
		return response;
	}


	@Override
	public SingleResponse<OrdenesEntity> actualizarOrden(OrdenActualizarDTO ordenActualizar) {
		DireccionEntity direccionCompra = direccionesServices.consultarDireccionePorId(ordenActualizar.getIdDireccion()).getResponse();
		ResponseListarCarrito carrito = carritoCompraService.mostrarCarrito(ordenActualizar.getIdCliente()).getResponse();
		ClienteEntity cliente = carrito.getCarrito().get(0).getCliente();
		
		Optional<OrdenesEntity> opcionalOrden = Optional.empty();
		
		try {
			opcionalOrden = ordenRepository.findById(ordenActualizar.getIdOrden());
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el id de la orden en la BD");
		}
		
		if(opcionalOrden.isEmpty()) {
			throw new BusinessException(HttpStatus.NOT_FOUND, "Error al buscar la orden en la BD");
		}
		
		OrdenesEntity nuevaOrden = opcionalOrden.get();
		nuevaOrden.setCalle(direccionCompra.getCalle());
		nuevaOrden.setCiudad(direccionCompra.getCiudad());
		nuevaOrden.setCliente(cliente);
		nuevaOrden.setCodigoPostal(direccionCompra.getCodigoPostal());
		nuevaOrden.setColonia(direccionCompra.getColonia());
		nuevaOrden.setEntreCalle1(direccionCompra.getEntreCalle1());
		nuevaOrden.setEntreCalle2(direccionCompra.getEntreCalle2());
		nuevaOrden.setEstatusOrden(EstatusOrden.NUEVO);
		nuevaOrden.setFechaOrden(LocalDateTime.now());
		nuevaOrden.setImpuesto(ordenActualizar.getIva());
		nuevaOrden.setMetodoPago(ordenActualizar.getMetodoPago());
		nuevaOrden.setNumeroExterior(direccionCompra.getNumeroExterior());
		nuevaOrden.setNumeroInterior(direccionCompra.getNumeroInterior());
		nuevaOrden.setSubTotal(ordenActualizar.getTotalProductos());
		nuevaOrden.setTelefono(direccionCompra.getTelefono());
		nuevaOrden.setTotal(ordenActualizar.getPagoTotal());
		
		try {
			nuevaOrden = ordenRepository.save(nuevaOrden);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar la orden en la BD");
		}
		
		OrdenesEntity ordenGuardada = nuevaOrden;
		Set<OrdenDetalleEntity> detallesOrden = new HashSet<>();
		carrito.getCarrito().forEach(carroProducto->{
			OrdenDetalleEntity ordenDetalle = new OrdenDetalleEntity();
			ordenDetalle.setCantidad(carroProducto.getCantidad());
			ordenDetalle.setOrden(ordenGuardada);
			if(carroProducto.getProducto().getPrecioOferta() != null) {
				ordenDetalle.setPrecio(carroProducto.getProducto().getPrecioOferta());
			}else {
				ordenDetalle.setPrecio(carroProducto.getProducto().getPrecioVenta());
			}
			ordenDetalle.setProducto(carroProducto.getProducto());
			ordenDetalle.setSubtotal(carroProducto.getSubtotal());
			try {
				detallesOrden.add(ordenDetalleRepository.save(ordenDetalle));
				
			} catch (DataAccessException ex) {
				log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
						ex.getStackTrace());
				throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el datelle de la orden en la BD");
			}
		});
		ordenGuardada.setOrdenDetalle(detallesOrden);

		SingleResponse<OrdenesEntity> response = new SingleResponse<OrdenesEntity> ();
		response.setMensaje("Lo orden se actualizo correctamente");
		response.setOk(true);
		response.setResponse(ordenGuardada);
		return response;
	}


	@Override
	public SingleResponse<OrdenDetalleEntity> agregarProductoOrdenDetalle(OrdenDetalleAgregarProductoDTO orden) {
		Optional<OrdenesEntity> ordenOpcional = Optional.empty();
		Optional<ProductosEntity> productoOpcional =  Optional.empty();
		
		
		try {
			ordenOpcional = ordenRepository.findById(orden.getIdOrdenEntity());
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el id de la Orden en la BD");		
		
		}

		if(ordenOpcional.isEmpty()) {
			throw new BusinessException(HttpStatus.NOT_FOUND, "Error no se encontro la orden en la BD");		
		}
		
		try {
			productoOpcional = productoRepository.findById(orden.getIdProducto());
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + excepcion,
					excepcion.getStackTrace());
		throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el id del producto en la BD");				}
		
		
		if(productoOpcional.isEmpty()) {
			throw new BusinessException(HttpStatus.NOT_FOUND, "Error no se encontro el producto en la BD");		
		}
		
		OrdenDetalleEntity ordenDetalleNueva = new OrdenDetalleEntity();
		ordenDetalleNueva.setOrden(ordenOpcional.get());
		ordenDetalleNueva.setProducto(productoOpcional.get());
		ordenDetalleNueva.setCantidad(orden.getCantidad());
		
		ProductosEntity producto = ordenDetalleNueva.getProducto();
		
		if(producto.getPrecioOferta() != null) {
			ordenDetalleNueva.setPrecio(producto.getPrecioOferta());
		}else {
			ordenDetalleNueva.setPrecio(producto.getPrecioVenta());
		}
		
		Double subtotal = orden.getCantidad() * ordenDetalleNueva.getPrecio();
		ordenDetalleNueva.setSubtotal(subtotal);	
		
		
		try {
			ordenDetalleNueva = ordenDetalleRepository.save(ordenDetalleNueva);
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el id de la Orden en la BD");		
		}
		
		
		try {
			ordenOpcional = ordenRepository.findById(orden.getIdOrdenEntity());
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el id de la Orden en la BD");		
		
		}
		
		
		OrdenesEntity ordenNuevo = ordenOpcional.get();
//		Set<OrdenDetalleEntity> ordenDetalle = new HashSet<>();
//		ordenDetalle.add(ordenDetalleNueva);
//		ordenNuevo.setOrdenDetalle(ordenDetalle);

		Double subTotal = 0.0;
		
		for (OrdenDetalleEntity ordenDetalleEntity : ordenNuevo.getOrdenDetalle()) {
			subTotal += ordenDetalleEntity.getSubtotal();
		}
		ordenNuevo.setSubTotal(subTotal);			
		Double IVA = ordenNuevo.getSubTotal() * orden.getIva();
		ordenNuevo.setTotal(ordenNuevo.getSubTotal() + IVA);
		
		try {
			ordenNuevo = ordenRepository.save(ordenNuevo);
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar la Orden en la BD");
		}
		
		SingleResponse<OrdenDetalleEntity> response = new SingleResponse<OrdenDetalleEntity>();
		response.setMensaje("El Producto se agrego correctamente");
		response.setOk(true);
		response.setResponse(ordenDetalleNueva);		
		return response;
	}


	@Override
	public SingleResponse<OrdenDetalleEntity> eliminarProductoOrdenDetalle(Integer idOrden, Integer idOrdenDetalle, Double iva) {
		Optional<OrdenesEntity> ordenOpcional = Optional.empty();
		Optional<OrdenesEntity> ordenOpcional2 = Optional.empty();
		Optional<OrdenDetalleEntity> ordenDetalleOpcional = Optional.empty();

		
		try {
			ordenOpcional = ordenRepository.findById(idOrden);
		} catch (DataAccessException excepcion) {
			log.error("Ha occurido inesperado. Excpetion {} {} ",excepcion.getMessage() + "" + excepcion,
					excepcion.getStackTrace());		
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar la Orden existente en la BD");
		}
		
		if(ordenOpcional.isEmpty()) {
			throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontró la orden");
		}
		
		
		try {
			ordenDetalleOpcional = ordenDetalleRepository.findById(idOrdenDetalle);
		} catch (DataAccessException excepcion) {
			log.error("Ha occurido inesperado. Excpetion {} {} ",excepcion.getMessage() + "" + excepcion,
					excepcion.getStackTrace());		
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar la Orden detalle existente en la BD");
		}
		
		if(ordenDetalleOpcional.isEmpty()) {
			throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontró la orden detalle");
		}
		
		try {
			ordenDetalleRepository.deleteById(ordenDetalleOpcional.get().getIdOrdenDetalle());
		} catch (DataAccessException excepcion) {
			log.error("Ha occurido inesperado. Excpetion {} {} ",excepcion.getMessage() + "" + excepcion,
					excepcion.getStackTrace());		
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar la Orden existente en la BD");
		}
		
		//SE busca de nuevo para obtener los nuevos ordenes detalle
		try {
			ordenOpcional2 = ordenRepository.findById(idOrden);
		} catch (DataAccessException excepcion) {
			log.error("Ha occurido inesperado. Excpetion {} {} ",excepcion.getMessage() + "" + excepcion,
					excepcion.getStackTrace());		
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar la Orden existente en la BD");
		}
		
		
		OrdenesEntity ordenNuevo = ordenOpcional2.get();

		Double subTotal = 0.0;
		
		
		Set<OrdenDetalleEntity> ordenDetalle = ordenNuevo.getOrdenDetalle();
		ordenDetalle.remove(ordenDetalleOpcional.get());
		
		for (OrdenDetalleEntity ordenDetalleEntity : ordenDetalle) {
			subTotal += ordenDetalleEntity.getSubtotal();
		}
		ordenNuevo.setSubTotal(subTotal);			
		Double IVA = ordenNuevo.getSubTotal() * iva;
		ordenNuevo.setTotal(ordenNuevo.getSubTotal() + IVA);
		
		try {
			ordenNuevo = ordenRepository.save(ordenNuevo);
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar la Orden en la BD");
		}
		
		SingleResponse<OrdenDetalleEntity> response = new SingleResponse<OrdenDetalleEntity>();
		response.setMensaje("El producto se eliminó correctamente y la orden se actualizó");
		response.setOk(true);
		response.setResponse(ordenDetalleOpcional.get());
		
		return response;
	}	

}
