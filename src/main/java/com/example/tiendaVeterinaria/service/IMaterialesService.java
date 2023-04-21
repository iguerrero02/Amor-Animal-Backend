package com.example.tiendaVeterinaria.service;

import java.util.List;

import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.MaterialesEntity;

public interface IMaterialesService {
	
	SingleResponse<List<MaterialesEntity>> consultarMateriales();
	SingleResponse<MaterialesEntity> crearMateriales(MaterialesEntity mat);
	SingleResponse<List<MaterialesEntity>> consultarMaterialesActivos();
	SingleResponse<MaterialesEntity> actualizarMaterial(MaterialesEntity mat);

}
