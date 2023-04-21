package com.example.tiendaVeterinaria.service;

import java.util.List;

import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.OfertaEntity;

public interface IOfertaService {

	SingleResponse<List<OfertaEntity>> consultarTodosOferta();
	SingleResponse<OfertaEntity> guardarOferta(OfertaEntity oferta, Integer idProducto);
	SingleResponse<OfertaEntity> actualizarOferta(OfertaEntity oferta, Integer idProductoS);
	SingleResponse<List<OfertaEntity>> consultarTodosActivos();
	SingleResponse<OfertaEntity> actualizarOfertaEstatus(Integer idOferta, Integer estatus);
	SingleResponse<OfertaEntity> busquedaPorIdProducto(Integer idProducto);
	void actualizarOfertasVencidas();
}
