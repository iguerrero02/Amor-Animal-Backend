package com.example.tiendaVeterinaria.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "COLORES")
@Getter
@Setter

public class ColoresEntity implements Serializable {
	
	/**
	 * Variable para serializar la clase
	 */
	private static final long serialVersionUID = -1749347161750703031L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_COLOR", length = 1)
	private Integer idColor;
	
	@Column(name = "DESCRIPCION", length = 100)
	private String descripcionColor;
	
	@Column(name = "NOMBRE", length = 50, unique = true, nullable = false)
	private String nombreColor;
	
	@Column(name = "CODIGO_HEXADECIMAL",length = 50)
	private String codigoHexadecimal;
	
	@Column(name = "ESTATUS", nullable = false, length = 1)
	private Integer estatus;
	
//	/**
//	 * Relación Uno a Muchos
//	 * Lista de asuntos
//	 * */
//	@OneToMany(mappedBy = "color")
//	@JsonBackReference
//	private List<ProductosEntity> productos;
	

}
