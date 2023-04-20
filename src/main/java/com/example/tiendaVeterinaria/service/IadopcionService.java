package com.example.tiendaVeterinaria.service;

import java.util.List;

import com.example.tiendaVeterinaria.model.Adopcion;
import com.example.tiendaVeterinaria.model.AdopcionRequest;
import com.example.tiendaVeterinaria.model.Response;

public interface IadopcionService {

	public Adopcion guardarAdopcion(Adopcion adopcion);
	public List<Adopcion> findAll();
}
