package com.example.tiendaVeterinaria.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.tiendaVeterinaria.model.dto.ClienteDireccion;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ClienteEntity;

public interface IClienteService {
	
	SingleResponse<List<ClienteEntity>> consultarClientes();
	SingleResponse<ClienteEntity> consultarClientePorId(Integer clienteid);
	SingleResponse<ClienteEntity> crearCliente(ClienteEntity clienteDireccion);
	SingleResponse<ClienteEntity> eliminarCliente(Integer id);
	SingleResponse<ClienteEntity> actualizarCliente(ClienteEntity cliente);
	SingleResponse<List<ClienteEntity>> consultarClientesActivos();
	SingleResponse<Page<ClienteEntity>> consultarPorPaginas(int noPagina, String campo, String direccion, String buscar);
	SingleResponse<ClienteEntity> loginCliente(ClienteEntity usuario);
}
