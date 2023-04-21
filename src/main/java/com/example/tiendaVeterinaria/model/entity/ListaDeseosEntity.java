package com.example.tiendaVeterinaria.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "LISTA_DESEOS")
@Getter
@Setter
public class ListaDeseosEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8175188116125218534L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_LISTA_DESEO", length = 11)
	private Integer idListaDeseo;
	
	@Column(name = "FECHA")
	private LocalDateTime fecha;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_CLIENTE" , nullable = true)
	@JsonManagedReference
	private ClienteEntity cliente;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_PRODUCTO" , nullable = true)
	@JsonManagedReference
	private ProductosEntity producto;
	
	
	

}
