package com.example.tiendaVeterinaria.service;



import com.example.tiendaVeterinaria.model.dto.CarroComprasRequest;
import com.example.tiendaVeterinaria.model.dto.ResponseListarCarrito;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.CarroComprasEntity;


public interface ICarritoCompraService {
	
	SingleResponse<CarroComprasEntity> agregarProducto(CarroComprasRequest request);
	SingleResponse<ResponseListarCarrito> mostrarCarrito(Integer idCliente);
	SingleResponse<ResponseListarCarrito> actualizarCantidad(CarroComprasRequest request);
	SingleResponse<ResponseListarCarrito> eliminarProducto(Integer idCarrito);

}
