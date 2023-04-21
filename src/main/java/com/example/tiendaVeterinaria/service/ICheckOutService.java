package com.example.tiendaVeterinaria.service;

import java.util.List;

import com.example.tiendaVeterinaria.model.dto.CheckOutInfo;
import com.example.tiendaVeterinaria.model.dto.ResponseListarCarrito;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.CarroComprasEntity;

public interface ICheckOutService {
	
	SingleResponse<CheckOutInfo> prepararCheckOut(Integer idClientes);

}
