package com.example.tiendaVeterinaria.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CarroComprasRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1058905412206017447L;
	
	private Integer idProducto;
	
	private Integer idCliente;
	
	private Integer cantidad;

}
