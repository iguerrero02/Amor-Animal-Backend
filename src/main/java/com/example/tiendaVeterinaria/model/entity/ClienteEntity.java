package com.example.tiendaVeterinaria.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CLIENTES")
@Data
public class ClienteEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7044564663461049158L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CLIENTE", length = 6)
	private Integer idCliente;
	
	@Column(name = "CORREO_ELECTRONICO", unique = true, nullable = false, length = 50)
	private String correoElectronico;
	
	@Column(name = "PASSWORD", nullable = false, length = 64)
	private String password;
	
	@Column(name = "CODIGO_VERIFICACION", nullable = false, length = 64)
	private String codigoVerificacion;
	
	@Column(name = "NOMBRE", nullable = false, length = 50)
	private String nombre;
	
	@Column(name = "APELLIDO_PATERNO", nullable = false, length = 30)
	private String apellidoPaterno;
	
	@Column(name = "APELLIDO_MATERNO", nullable= false, length = 30)
	private String apellidoMaterno;
	
	@Column(name = "TIPO",  length = 30)
	private String tipo;
	
	@Column(name = "TELEFONO", nullable = false, length = 10)
	private String telefono;
	
	@Column(name = "ESTATUS", nullable = false, length = 1)
	private Integer estatus;
	
	@Column(name = "BYTES_IMAGEN", length = 100)
	@Lob
	private byte[] bytesImagen;
	
	@Column(name = "NOMBRE_IMAGEN", length = 100)
	private String nombreImagen;
	
	@Column(name = "TIPO_IMAGEN", length = 100)
	private String tipoImagen;
	
	@Transient
	//@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private transient MultipartFile multipartFile;
	/**
	 * Relación Uno a Muchos
	 * Lista de asuntos
	 * */
//	@OneToMany(mappedBy = "cliente")
//	@JsonBackReference
//	@JsonIgnore
//	private List<DireccionEntity> direcciones;
	
//	/**
//	 * Relación Uno a Muchos
//	 * Lista de asuntos
//	 * */
//	@OneToMany(mappedBy = "cliente")
//	@JsonBackReference
//	private List<CarroComprasEntity> carroCompras;
//	
//	/**
//	 * Relación Uno a Muchos
//	 * Lista de asuntos
//	 * */
//	@OneToMany(mappedBy = "cliente")
//	@JsonBackReference
//	private List<ListaDeseosEntity> listaDeseos;


	

}
