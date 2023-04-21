package com.example.tiendaVeterinaria.service;

import java.util.List;

import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.RolEntity;

public interface IRolesService {
	
	SingleResponse<List<RolEntity>> consultarRoles();
	SingleResponse<RolEntity> crearRoles(RolEntity rol);
	SingleResponse<List<RolEntity>> consultarRolesActivos();
	SingleResponse<RolEntity> actualizarEstatusRol(RolEntity rol);

}
