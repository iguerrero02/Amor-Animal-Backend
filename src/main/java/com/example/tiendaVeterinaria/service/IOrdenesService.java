package com.example.tiendaVeterinaria.service;

import org.springframework.data.domain.Page;

import com.example.tiendaVeterinaria.model.dto.CrearOrdenRequest;
import com.example.tiendaVeterinaria.model.dto.OrdenActualizarDTO;
import com.example.tiendaVeterinaria.model.dto.OrdenDetalleAgregarProductoDTO;
import com.example.tiendaVeterinaria.model.dto.OrdenDto;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.OrdenDetalleEntity;
import com.example.tiendaVeterinaria.model.entity.OrdenesEntity;


public interface IOrdenesService {
	
	SingleResponse<Page<OrdenesEntity>> consultarPorPaginas(int noPagina, String campo, String direccion, String buscar);
	SingleResponse<OrdenDto> detalleOrden (Integer idOrden);
	SingleResponse<OrdenDto> crearOrden (CrearOrdenRequest request);
	SingleResponse<OrdenesEntity> eliminarOrden(Integer idOrden);
	SingleResponse<OrdenesEntity> actualizarOrden(OrdenActualizarDTO ordenActualizar);
	SingleResponse<OrdenDetalleEntity> agregarProductoOrdenDetalle(OrdenDetalleAgregarProductoDTO orden);
	SingleResponse<OrdenDetalleEntity> eliminarProductoOrdenDetalle(Integer idOrden, Integer idOrdenDetalle, Double iva);
	
}
