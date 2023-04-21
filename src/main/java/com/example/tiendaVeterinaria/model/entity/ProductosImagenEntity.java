package com.example.tiendaVeterinaria.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "IMAGENES_PRODUCTOS")
@Getter
@Setter

public class ProductosImagenEntity implements Serializable {

	/**
	 * Variable para serializar la clase
	 */
	private static final long serialVersionUID = -1749347161750703031L;
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ID_IMAGEN")
	    private Integer idImagen;
	    
	    @Column(name = "NOMBRE_IMAGEN", length = 100)
	    private String nombreImagen;
	    
	    @Column(name = "TIPO_IMAGEN", length = 10)
	    private String tipoImagen;
	    
	    @Column(name = "IMAGEN_PREDETERMINADO")
	    private Integer imagenPredeterminado;
	    
	    @Column(name = "IMAGEN_BITS")
	    @Lob
	    private byte[] imagenBits;
	    
	    @Column(name = "ESTATUS", length = 1)
	    private Integer estatus;
	    
	    @ManyToOne(optional = false)
	    @JoinColumn(name = "ID_PRODUCTO")
	    private ProductosEntity idProducto;
	
	
	
	
}
