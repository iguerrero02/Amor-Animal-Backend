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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "CARRO_COMPRAS")
@Getter
@Setter
public class CarroComprasEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8218894604872673600L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CARRO_COMPRA", length = 11)
	private Integer idCarroCompra;
	
	@Column(name = "CANTIDAD", nullable = false, length = 5)
	private Integer cantidad;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_CLIENTE" , nullable = false)
	@JsonManagedReference
	private ClienteEntity cliente;
	
	

	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_PRODUCTO" , nullable = false)
	@JsonManagedReference
	private ProductosEntity producto;
	
	@Transient
	public double getSubtotal() {		
		if(producto.getPrecioOferta() != null) {
			return  producto.getPrecioOferta() * cantidad;
		}else {
			return producto.getPrecioVenta() * cantidad;
		}
	}
}
