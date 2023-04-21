package com.example.tiendaVeterinaria.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.example.tiendaVeterinaria.model.dto.EstatusOrden;
import com.example.tiendaVeterinaria.model.dto.MetodoPago;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ORDENES")
@Getter
@Setter


public class OrdenesEntity implements Serializable{

	
	/**
	 * Variable para serializar la clase
	 */
	private static final long serialVersionUID = -1749347161750703031L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ORDEN", length = 1)
	private Integer idOrden;
	
	@Column(name = "CALLE", nullable = false, length = 50)
	private String calle;
	
	@Column(name = "NUMERO_EXTERIOR", nullable = false, length = 5)
	private String numeroExterior;
	
	@Column(name = "NUMERO_INTERIOR",  length = 5)
	private String numeroInterior;
	
	@Column(name = "CODIGO_POSTAL", nullable = false, length = 5)
	private Integer codigoPostal;
	
	@Column(name = "COLONIA", nullable = false, length = 50)
	private String colonia;
	
	@Column(name = "ENTRE_CALLE1", length = 50)
	private String entreCalle1;
	
	@Column(name = "ENTRE_CALLE2", length = 50)
	private String entreCalle2;
		
	@Column(name = "CIUDAD", nullable = false, length = 50)
	private String ciudad;
	
	@Column(name = "TELEFONO", nullable = false, length = 10)
	private String telefono;
	
	@Column(name = "IMPUESTO", length = 10)
	private Double impuesto;
	
	@Column(name = "SUB_TOTAL", length = 10)
	private Double subTotal;
	
	@Column(name = "TOTAL", length = 10)
	private Double total;
	
	@Column(name = "FECHA", nullable = false)
	private LocalDateTime fechaOrden;
	
	@Enumerated(EnumType.STRING)
	private MetodoPago metodoPago;
	
	@Enumerated(EnumType.STRING)
	private EstatusOrden estatusOrden;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_CLIENTE" , nullable = false)
	@JsonManagedReference
	private ClienteEntity cliente;
	
	/**
	 * Relación Uno a Muchos
	 * Lista de asuntos
	 * */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "orden")
//	@Fetch(value = FetchMode.SUBSELECT)
	@JsonBackReference
	private Set<OrdenDetalleEntity> ordenDetalle = new HashSet<>();
}
