package com.example.tiendaVeterinaria.service;

import java.util.List;

import com.example.tiendaVeterinaria.model.dto.ClienteDireccion;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ClienteEntity;
import com.example.tiendaVeterinaria.model.entity.DireccionEntity;

public interface IDireccionesServices {

	public SingleResponse<DireccionEntity> crearDireccion(DireccionEntity direccion, Integer cliente);
	SingleResponse<List<DireccionEntity>> consultarDireccionesPorCliente(Integer idCliente);
	SingleResponse<DireccionEntity> eliminarDireccion(Integer id);
	SingleResponse<DireccionEntity> crearNuevaDireccion(DireccionEntity cliente, ClienteEntity idCliente);
	SingleResponse<DireccionEntity> consultarDireccionDefecto(Integer idCliente);
	public SingleResponse<DireccionEntity> actualizarDireccion(DireccionEntity direccion,ClienteEntity idCliente);
	SingleResponse<DireccionEntity> consultarDireccionePorId(Integer direccionid);
}
