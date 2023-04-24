package com.example.tiendaVeterinaria.service;

import java.util.List;

import com.example.tiendaVeterinaria.model.dto.ReseniaOrdenDTO;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ReseniaEntity;

public interface IReseniaService {
	
	public SingleResponse<ReseniaEntity> crearResenia(ReseniaOrdenDTO resenia);
	SingleResponse<List<ReseniaEntity>> consultarResenia();
	SingleResponse<ReseniaEntity> consultarReseniaClienteProducto(Integer idCliente,Integer idProducto);
	SingleResponse<List<ReseniaEntity>> consultarReseniaCliente(Integer idCliente);
	SingleResponse<ReseniaEntity> consultarReseniaDetalle(Integer idResenia);
	SingleResponse<ReseniaEntity> actualizarResenia(ReseniaEntity resenia);
	SingleResponse<ReseniaEntity>  borrarResenia(Integer idResenia);
}

