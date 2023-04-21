package com.example.tiendaVeterinaria.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdenDetalleAgregarProductoDTO {
	
	private Integer idOrdenEntity;
	
	private Integer idProducto;
	
	private Integer cantidad;
	
	private Double iva;

}
