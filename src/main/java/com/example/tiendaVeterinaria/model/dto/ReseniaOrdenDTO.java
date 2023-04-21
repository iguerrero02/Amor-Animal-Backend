package com.example.tiendaVeterinaria.model.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReseniaOrdenDTO {

	private String encabezado;
	
	private String comentarios;
	
	private Integer valoracion;
	
	private Integer idCliente;
	
	private Integer idProducto;

 
}
