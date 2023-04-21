package com.example.tiendaVeterinaria.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ORDEN_DETALLE")
@Data

public class OrdenDetalleEntity implements Serializable {
	
	/**
	 * Variable para serializar la clase
	 */
	private static final long serialVersionUID = -1749347161750703031L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ORDEN_DETALLE", length = 1)
	private Integer idOrdenDetalle;
	
	@Column(name = "CANTIDAD", nullable = false)
	private Integer cantidad;
	
	@Column(name = "SUBTOTAL", length = 10, nullable = false)
	private Double subtotal;
	
	@Column(name = "PRECIO", length = 10, nullable = false)
	private Double precio;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_ORDEN" , nullable = false)
	@JsonManagedReference
	private OrdenesEntity orden;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_PRODUCTO" , nullable = false)
//	@JsonManagedReference
	private ProductosEntity producto;

}
