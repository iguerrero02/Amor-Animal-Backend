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
@Table(name = "CATEGORIAS")
@Getter
@Setter

public class CategoriasEntity implements Serializable {
	
	/**
	 * Variable para serializar la clase
	 */
	private static final long serialVersionUID = -1749347161750703031L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CATEGORIAS", length = 1)
	private Integer idCategorias;
	
	@Column(name = "DESCRIPCION", length = 500)
	private String descripcionCategoria;
	
	@Column(name = "NOMBRE", length = 100, unique = true, nullable = false)
	private String nombreCategoria;
	
	@Column(name = "DETALLES", length = 1000)
	private String detallesCategoria;
	
	@Column(name = "CODIGO_REFERENCIA", length = 10, unique = true, nullable = false)
	private String codigoReferencia;
	
	@Column(name = "ID_CATEGORIA_PADRE", length = 1 )
	private Integer idCategoriaPadre;
	
	@Column(name = "ESTATUS",nullable = false, length = 1)
	private Integer estatus;
	

}
