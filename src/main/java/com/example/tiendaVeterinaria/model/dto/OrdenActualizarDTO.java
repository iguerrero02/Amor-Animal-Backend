package com.example.tiendaVeterinaria.model.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrdenActualizarDTO {

	private Integer idOrden;
	
	private Integer idCliente;
	
	private Integer idDireccion;
	
	private double totalProductos;
	
	private double iva;
	
	private double pagoTotal;
	
	private MetodoPago metodoPago;
	


}
