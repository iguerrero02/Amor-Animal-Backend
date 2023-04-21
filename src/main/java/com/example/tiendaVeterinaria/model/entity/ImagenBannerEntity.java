package com.example.tiendaVeterinaria.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "IMAGEN_BANNER")
public class ImagenBannerEntity implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_BANNER")
	private Integer idBanner;
	
	@Column(name = "NOMBRE_ARCHIVO")
	private String nombreArchivo;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	@Column(name = "TIPO_ARCHIVO")
	private String tipoArchivo;
	
//	esto es útil para almacenar datos de gran tamaño como imágenes, archivos PDF, documentos de texto, etc.
	@Lob
	@Column(name = "IMAGEN_BITS" )
	private byte[] imagenBits;
	
	@Column(name = "ESTATUS")
	private Integer estatus;
		 
}
