package com.example.tiendaVeterinaria.model;

public class AdopcionRequest {
	private Integer id_adopcion;
	private String nombre;
	private String apellido;
	private String direccion;
	private String ciudad;
	private String codigoPostal;
	private Integer edad;
	private Integer mascota;
	public Integer getId_adopcion() {
		return id_adopcion;
	}
	public void setId_adopcion(Integer id_adopcion) {
		this.id_adopcion = id_adopcion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public Integer getEdad() {
		return edad;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	public Integer getMascota() {
		return mascota;
	}
	public void setMascota(Integer mascota) {
		this.mascota = mascota;
	}
	
	
	
}
