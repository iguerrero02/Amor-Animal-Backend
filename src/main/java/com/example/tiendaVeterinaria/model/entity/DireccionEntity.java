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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DIRECCIONES")
@Getter
@Setter
public class DireccionEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3702369019178344288L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_DIRECCION", length = 6)
	private Integer idDireccion;
	
	@Column(name = "CALLE", nullable = false, length = 50)
	private String calle;
	
	@Column(name = "NUMERO_EXTERIOR", nullable = false, length = 5)
	private String numeroExterior;
	
	@Column(name = "NUMERO_INTERIOR",  length = 5)
	private String numeroInterior;
	
	@Column(name = "CODIGO_POSTAL", nullable = false, length = 5)
	private Integer codigoPostal;
	
	@Column(name = "NOMBRE", nullable = false, length = 30)
	private String nombreDireccion;
	
	@Column(name = "COLONIA", nullable = false, length = 50)
	private String colonia;
	
	@Column(name = "ENTRE_CALLE1", length = 50)
	private String entreCalle1;
	
	@Column(name = "ENTRE_CALLE2", length = 50)
	private String entreCalle2;
	
	@Column(name = "PREDETERMINADO", nullable = false, length = 1)
	private Integer predeterminado;
	
	@Column(name = "ESTATUS", nullable = false, length = 1)
	private Integer estatus;
	
	@Column(name = "CIUDAD", nullable = false, length = 50)
	private String ciudad;
	
	@Column(name = "TELEFONO", nullable = false, length = 10)
	private String telefono;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_CLIENTE" , nullable = true)
	@JsonManagedReference
	@JsonIgnore
	private ClienteEntity cliente;
	

}
