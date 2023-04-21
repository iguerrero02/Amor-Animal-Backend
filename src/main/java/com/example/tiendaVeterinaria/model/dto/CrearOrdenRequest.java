package com.example.tiendaVeterinaria.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearOrdenRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idCliente;
	
	private Integer idDireccion;
	
	private double totalProductos;
	
	private double iva;
	
	private double pagoTotal;
	
	private MetodoPago metodoPago;

}
