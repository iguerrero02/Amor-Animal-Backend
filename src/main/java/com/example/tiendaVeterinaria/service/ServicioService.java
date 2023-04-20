package com.example.tiendaVeterinaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tiendaVeterinaria.model.Servicio;
import com.example.tiendaVeterinaria.repository.ServicioRepository;

import java.util.List;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Servicio> getAllServicios() {
        return servicioRepository.findAll();
    }

    public Servicio getServicioById(Integer id_servicio) {
        return servicioRepository.findById(id_servicio).orElse(null);
    }

    public Servicio saveServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public void deleteServicioById(Integer id_servicio) {
        servicioRepository.deleteById(id_servicio);
    }
}

