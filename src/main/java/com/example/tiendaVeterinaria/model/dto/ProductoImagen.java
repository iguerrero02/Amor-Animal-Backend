package com.example.tiendaVeterinaria.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase dto para el anexo del archivo
 * @author Indra
 *
 */
@Getter
@Setter
public class ProductoImagen implements Serializable{

	/**
	 * Variable para serializar la clase.
	 */
	private static final long serialVersionUID = -7250216422252564289L;
	
	/**
	 * nombre del archivo
	 */
	@NotBlank(message = "El nombre del archivo no debe estar vacio")
	@Size(min= 3, max = 100, message = "El nombre del archivo debe tener entre 3 y 100 caracteres.")
	 private String nombreImagen;
	
	/**
	 * archivo en base64
	 */
	@NotBlank(message = "No se ha incluido ninguna imagen")
	private String imagen;
	
	/**
	 * extension del archivo
	 */
	@NotBlank
	@Size(max = 100, message = "La extensión del archivo no debe superar los 100 caracteres")
	 private String tipoImagen;
	
	 private Integer estatus;
	 
	 private Integer imagenPredeterminado;
	
}
