package com.example.tiendaVeterinaria.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PRODUCTOS")
@Getter
@Setter

public class ProductosEntity implements Serializable {

	/**
	 * Variable para serializar la clase
	 */
	private static final long serialVersionUID = -1749347161750703031L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PRODUCTO", length = 1)
	private Integer idProducto;
	
	@Column(name = "CODIGO_REFERENCIA", length = 10)
	private String codigoReferencia;
	
	@Column(name = "NOMBRE", length = 100)
	private String nombreProducto;
	
	@Column(name = "DESCRIPCION_BREVE", length = 150)
	private String descripcionBreve;
	
	@Column(name = "LARGO_INTERIOR", length = 4)
	private Double largoInterior;
	
	@Column(name = "LARGO_EXTERIOR", length = 4)
	private Double largoExterior;
	
	@Column(name = "ANCHO_INTERIOR", length = 4)
	private Double anchoInterior;
	
	@Column(name = "ANCHO_EXTERIOR", length = 4)
	private Double anchoExterior;
	
	@Column(name = "ALTO_INTERIOR", length = 4)
	private Double altoInterior;
	
	@Column(name = "ALTO_EXTERIOR", length = 4)
	private Double altoExterior;

	
	@Column(name = "PRECIO_COMPRA" , length = 6)
	private Double precioCompra;
	
	@Column(name = "PRECIO_OFERTA" , length = 6)
	private Double precioOferta;
	
	@Column(name = "PRECIO_VENTA" , length = 6)
	private Double precioVenta;
	
	@Column(name = "FECHA_ALTA")
	private LocalDateTime fechaAlta;
	
	@Column(name = "FECHA_MODIFICACION")
	private LocalDateTime fechaModificacion;
	
	@Column(name = "PESO" , length = 5)
	private Double peso;
	
	@Column(name = "ESTATUS")
	private Integer estatus;
	
	
//	/**
//	 * Relación Uno a Muchos
//	 * Lista de asuntos
//	 * */
//	@OneToMany(mappedBy = "producto")
//	@JsonBackReference
//	private List<OrdenDetalleEntity> ordenDetalle;
	
	
//	@ManyToOne(optional = false)
//	@JoinColumn(name = "ID_MATERIAL" , nullable = true)
//	//@JsonManagedReference
//	private MaterialesEntity material;
//	
//	@ManyToOne(optional = false)
//	@JoinColumn(name = "ID_COLOR", nullable = false)
//	//@JsonManagedReference
//	private ColoresEntity color;
//	
//	@ManyToOne(optional = false)
//	@JoinColumn(name = "ID_CATEGORIAS" , nullable = true)
//	//@JsonManagedReference
//	private CategoriasEntity categoria;
//	
//	@OneToOne(optional = true)
//	@JoinColumn(name = "ID_OFERTA")
//	private OfertaEntity oferta;
	
}
