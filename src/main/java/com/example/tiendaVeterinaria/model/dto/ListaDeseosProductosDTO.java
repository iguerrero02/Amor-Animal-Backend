package com.example.tiendaVeterinaria.model.dto;

import java.time.LocalDateTime;
import java.util.List;


import com.example.tiendaVeterinaria.model.entity.ProductosEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListaDeseosProductosDTO {
	
	
	private Integer idListaDeseo;
	
	private LocalDateTime fecha;
	
	private ProductosEntity producto;

}
