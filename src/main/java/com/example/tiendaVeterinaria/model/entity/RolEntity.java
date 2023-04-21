package com.example.tiendaVeterinaria.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;


import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ROLES")
@Getter
@Setter
public class RolEntity implements Serializable{

	/**
	 * Variable para serializar la clase
	 */
	private static final long serialVersionUID = -1749347161750703031L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ROL", length = 1)
	private Integer idRol;
	
	@Column(name = "NOMBRE", unique = true, nullable = false, length = 20)
//	@NotBlank(message = "El nombre del rol no debe estar vacío.")
//	@Size(min= 3, max = 100, message = "El nombre del rol  debe tener entre 3 y 20 caracteres.")
	private String nombreRol;
	
	@Column(name = "DESCRIPCION", nullable = false, length = 100)
//	@NotBlank(message = "La descripcion del rol no debe estar vacío.")
//	@Size(min= 3, max = 100, message = "La descripcion del rol  debe tener entre 10 y 100 caracteres.")
	private String descripcionRol;
	
	@Column(name = "ESTATUS", nullable = false, length = 1)
	private Integer estatus;
	
	/**
	 * Relación Uno a Muchos
	 * Lista de asuntos
	 * */
	@OneToMany(mappedBy = "rol")
	@JsonBackReference
	private List<UsuarioEntity> usuarios;

}
