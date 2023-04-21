package com.example.tiendaVeterinaria.service;

import java.util.List;

import com.example.tiendaVeterinaria.model.dto.ListaDeseosProductosDTO;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ListaDeseosEntity;

public interface IListaDeseosService {

	SingleResponse<List<ListaDeseosProductosDTO>> consultarTodos(Integer idCliente);
	SingleResponse<ListaDeseosEntity> guardarListaDeseo(Integer idCliente, Integer idProducto);
	SingleResponse<ListaDeseosEntity> eliminarListaDeseo(Integer idListaDeseo);
	
}
