package com.example.tiendaVeterinaria.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USUARIOS")
@Getter
@Setter
public class UsuarioEntity implements Serializable{

	/**
	 * Variable para serializar la clase.
	 */
	private static final long serialVersionUID = -8590131624056335066L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_USUARIO", length = 2)
	private Integer idUsuario;
	
	@Column(name = "NOMBRE", nullable = false, length = 50)
	private String nombreUsuario;
	
	@Column(name = "APELLIDO_PATERNO", nullable = false, length = 30)
	private String apellidoPaterno;
	
	@Column(name = "APELLIDO_MATERNO", length = 30)
	private String apellidoMaterno;
	
	@Column(name = "CORREO_ELECTRONICO", unique = true, length = 50)
	private String correoElectronico;
	
	@Column(name = "PASSWORD", length = 64)
	private String password;
	
	@Column(name = "NOMBRE_IMAGEN", length = 100)
	private String nombreImagen;
	
	@Column(name = "TIPO_IMAGEN", length = 100)
	private String tipoImagen;
	
	@Column(name = "BYTES_IMAGEN", length = 100)
	@Lob
	private byte[] bytesImagen;
	
	@Column(name = "ESTATUS", length = 1)
	private Integer estatus;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_ROL"	)
	//@JsonManagedReference
	private RolEntity rol;
	
	
	//@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Transient
	private transient MultipartFile multipartFile;
	
	@Transient
	private transient Integer idRolF;
	
	

}
