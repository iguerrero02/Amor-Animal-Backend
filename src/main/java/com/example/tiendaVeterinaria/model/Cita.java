package com.example.tiendaVeterinaria.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="cita")
public class Cita implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_cita;
	
	@Column(name = "nom_cliente")
	private String nom_cliente;
	
	@Column(name = "raza_mascota")
	private String raza_mascota;
	
	@Column(name = "nom_mascota")
	private String nom_mascota;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_servicio")
	private Servicio id_servicio;

	public Integer getId_cita() {
		return id_cita;
	}

	public void setId_cita(Integer id_cita) {
		this.id_cita = id_cita;
	}

	

	public String getNom_cliente() {
		return nom_cliente;
	}

	public void setNom_cliente(String nom_cliente) {
		this.nom_cliente = nom_cliente;
	}

	

	public Servicio getId_servicio() {
		return id_servicio;
	}

	public void setId_servicio(Servicio id_servicio) {
		this.id_servicio = id_servicio;
	}

	public String getRaza_mascota() {
		return raza_mascota;
	}

	public void setRaza_mascota(String raza_mascota) {
		this.raza_mascota = raza_mascota;
	}

	public String getNom_mascota() {
		return nom_mascota;
	}

	public void setNom_mascota(String nom_mascota) {
		this.nom_mascota = nom_mascota;
	}

	
	
}
