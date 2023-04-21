package com.example.tiendaVeterinaria.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "OFERTAS")
public class OfertaEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_OFERTA")
	private Integer idOferta;
	
	@Column(name = "TIPO_OFERTA", length = 50)
	private String tipoOferta;
	
	@Column(name = "DESCUENTO_PORCENTAJE", length = 10)
	private Integer descuentoEnPorcentaje;
	
	@Column(name = "FECHA_INICIO")
	private LocalDateTime fechaInicio;
	
	@Column(name = "FECHA_FIN")
	private LocalDateTime fechaFin;
	
	@Column(name = "CODIGO_OFERTA",length = 10)
	private String codigoOferta;
	
	@Column(name = "DESCRIPCION",length = 100)
	private String descripcion;
	
	@Column(name = "CONDICION_OFERTA", length = 100)
	private String condicionesOferta;
	
	@Column(name = "NUMERO_USO")
	private Integer numeroUso;
	
	@Column(name = "ESTATUS",length = 1)
	private Integer estatus;
	
	//para que el no se guarde en la base de datos
	@Column(name = "PRECIO_OFERTA")
	private Double precioConOferta;
	
	@OneToOne(optional = false)
	@JoinColumn(name = "ID_PRODUCTO")
	private ProductosEntity producto;
		
}