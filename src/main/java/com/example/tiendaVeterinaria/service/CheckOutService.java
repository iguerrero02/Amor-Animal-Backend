package com.example.tiendaVeterinaria.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tiendaVeterinaria.model.dto.CheckOutInfo;
import com.example.tiendaVeterinaria.model.dto.ResponseListarCarrito;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.DireccionEntity;


@Service
public class CheckOutService implements ICheckOutService{
	
	
	private static final Logger log = LoggerFactory.getLogger(CheckOutService.class);
	
	private final static String MX_CURRENCY_CODE = "MXN";
	
	private final static String CLIENT_ID = "AVkh3mDyLOq0pV1FLzEK6-0KWlvwUwiVCq8riWQZdg3XS_E1ILnzY3Dkt75KWSz92iv5dILuXR_z9wqh";

	@Autowired
	private IDireccionesServices direccionesServices;
	
	@Autowired
	private ICarritoCompraService carritoCompraService;
	

	@Override
	public SingleResponse<CheckOutInfo> prepararCheckOut(Integer idCliente) {
		
		ResponseListarCarrito carrito = carritoCompraService.mostrarCarrito(idCliente).getResponse();
		DireccionEntity direccionPredeterminada = direccionesServices.consultarDireccionDefecto(idCliente).getResponse();
		
		CheckOutInfo checkOutInfo = new CheckOutInfo();
		checkOutInfo.setCarrito(carrito);
		double totalProductos, iva, totalCompra;
		totalProductos = carrito.getTotalEstimado();
		iva = carrito.getTotalEstimado() * 0.16;
		totalCompra = totalProductos + iva;
		checkOutInfo.setTotalProductos(totalProductos);
		checkOutInfo.setIva(iva);
		checkOutInfo.setPagoTotal(totalCompra);
		checkOutInfo.setDireccion(direccionPredeterminada);
		checkOutInfo.setCliente(carrito.getCarrito().get(0).getCliente());
		checkOutInfo.setPaypalCurrencyCode(MX_CURRENCY_CODE);
		checkOutInfo.setPaypalClientId(CLIENT_ID);
		SingleResponse<CheckOutInfo> response = new SingleResponse<>();
		response.setMensaje("Check Out Info");
		response.setOk(true);
		response.setResponse(checkOutInfo);
		return response;
	}

}
