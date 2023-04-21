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

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "RESENIAS")
@Getter
@Setter
public class ReseniaEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2520031488532815675L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RESENIA", length = 10)
	private Integer idResenia;
	
	@Column(name = "ENCABEZADO", length = 150)
	private String encabezado;
	
	@Column(name = "COMENTARIOS", length = 500)
	private String comentarios;
	
	@Column(name = "VALORACION", nullable = false, length = 1)
	private Integer valoracion;
	
	@Column(name = "FECHA")
	private LocalDateTime fecha;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_CLIENTE" , nullable = true)
	//@JsonManagedReference
	private ClienteEntity cliente;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_PRODUCTO" , nullable = true)
	//@JsonManagedReference
	private ProductosEntity producto;
	


}
