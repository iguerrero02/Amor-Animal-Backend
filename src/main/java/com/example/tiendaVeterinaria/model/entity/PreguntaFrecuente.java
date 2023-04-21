package com.example.tiendaVeterinaria.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PREGUNTA_FRECUENTE")
public class PreguntaFrecuente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PREGUNTA_FRECUENTE")
	private Integer idPreguntaFrecuente;
	
	@Column(name = "PREGUNTA",length = 150,nullable = false)
	private String pregunta;
	
	@Column(name = "RESPUESTA",length = 500)
	private String respuesta;
	
	@Column(name = "ESTATUS",length = 1)
	private Integer estatus;
	
}
