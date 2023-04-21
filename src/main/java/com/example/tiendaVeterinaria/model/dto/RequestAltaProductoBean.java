package com.example.tiendaVeterinaria.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.tiendaVeterinaria.model.entity.CategoriasEntity;
import com.example.tiendaVeterinaria.model.entity.ColoresEntity;
import com.example.tiendaVeterinaria.model.entity.MaterialesEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase con el request para realizar el alta de un asunto
 * @author Indra
 *
 */
@Getter
@Setter
public class RequestAltaProductoBean implements Serializable{

	/**
	 * Variable para serializar la clase.
	 */
	private static final long serialVersionUID = -4391030134845159787L;
	
		
	/**
	 * Archivos anexos
	 */
	private List<ProductoImagen> imagenes = new ArrayList<>();
	
	private Integer idProducto;
	
	private String codigoReferencia;

	@NotBlank(message = "El nombre del producto no debe estar vacío.")
	@Size(min= 3, max = 100, message = "El nombre del producto debe tener entre 3 y 100 caracteres.")
	private String nombreProducto;

	@NotBlank(message = "La descripción breve no debe estar vacío.")
	@Size(min= 3, max = 100, message = "La descripción breve debe tener entre 3 y 100 caracteres.")
	private String descripcionBreve;

	private Double largoInterior;

	private Double largoExterior;

	private Double anchoInterior;;

	private Double anchoExterior;
	
	private Double altoInterior;
	
	private Double altoExterior;
	
	private Integer stock;
	
	private Double precioCompra;
	
	private Double precioVenta;
	
	private LocalDateTime fechaAlta;
	
	private LocalDateTime fechaModificacion;
	
	private Double peso;
	
	private Integer estatus;
	
	private MaterialesEntity material;
	
	private ColoresEntity color;

	private CategoriasEntity categoria;

}
