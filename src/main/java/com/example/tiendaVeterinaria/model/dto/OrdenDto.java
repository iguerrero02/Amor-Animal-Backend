package com.example.tiendaVeterinaria.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.example.tiendaVeterinaria.model.entity.ClienteEntity;
import com.example.tiendaVeterinaria.model.entity.OrdenDetalleEntity;

import lombok.Data;

@Data
public class OrdenDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8957420015245048980L;
	

	private Integer idOrden;
	
	
	private String calle;
	
	
	private String numeroExterior;
	
	
	private String numeroInterior;
	
	
	private Integer codigoPostal;
	
	
	private String colonia;
	
	
	private String entreCalle1;
	
	
	private String entreCalle2;
		
	
	private String ciudad;
	
	
	private String telefono;
	
	
	private Double impuesto;
	
	
	private Double subTotal;
	
	
	private Double total;
	
	
	private LocalDateTime fechaOrden;
	
	
	private MetodoPago metodoPago;
	
	
	private EstatusOrden estatusOrden;
	
	
	private ClienteEntity cliente;
	
	
	private Set<OrdenDetalleEntity> detalles = new HashSet<>();

}
