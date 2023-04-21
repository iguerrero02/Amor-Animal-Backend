package com.example.tiendaVeterinaria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiendaVeterinaria.exceptions.BusinessException;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.PreguntaFrecuente;
import com.example.tiendaVeterinaria.repository.IPreguntaFrecuenteRepository;

@Service
public class PreguntaFrecuenteService implements IPreguntaFrecuenteService{

	private static final Logger log = LoggerFactory.getLogger(PreguntaFrecuenteService.class);
	
	
	@Autowired
	private IPreguntaFrecuenteRepository preguntaFrecuenteRepository;

	@Override
	@Transactional
	public SingleResponse<PreguntaFrecuente> guardarPreguntaFrecuente(PreguntaFrecuente preguntaFrecuente) {
		
		preguntaFrecuente.setEstatus(1);
		try {
			preguntaFrecuente = preguntaFrecuenteRepository.save(preguntaFrecuente);						
		} catch (DataAccessException excepcion) {
			log.error("Ha occurrido un error inesperado. Excepcion {} {}" + excepcion.getMessage() + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar la pregunta frecuente");
		}
		
		SingleResponse<PreguntaFrecuente> response = new SingleResponse<PreguntaFrecuente>();		
		response.setOk(true);
		response.setMensaje("La pregunta frecuente se creo correctamente");
		response.setResponse(preguntaFrecuente);
		return response;
	}

	@Override
	public SingleResponse<List<PreguntaFrecuente>> consultaPreguntaFrecuentes() {

		List<PreguntaFrecuente> preguntasFrecuentes = new ArrayList<PreguntaFrecuente>();
		try {
			preguntasFrecuentes = preguntaFrecuenteRepository.findAll();
		} catch (DataAccessException exepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {}", exepcion.getMessage() + " " + exepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar las preguntas frecuentes");
		}
		SingleResponse<List<PreguntaFrecuente>> response = new SingleResponse<List<PreguntaFrecuente>>();
		if(!preguntasFrecuentes.isEmpty()) {
			response.setMensaje("Se obtuvo la lista de preguntas frecuentes con exito");
			response.setOk(true);
			response.setResponse(preguntasFrecuentes);
			return response;
		}
		throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontro ninguna pregunta frecuente");	
	}

	@Override
	public SingleResponse<PreguntaFrecuente> actualizarPreguntaFrecuente(PreguntaFrecuente preguntaFrecuente) {
		
		Optional<PreguntaFrecuente> preguntaOpcional = Optional.empty();
		
		try {
			preguntaOpcional = preguntaFrecuenteRepository.findById(preguntaFrecuente.getIdPreguntaFrecuente());
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {}", excepcion.getMessage() + " " + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar la pregunta frecuente");
		}
		
		if(preguntaOpcional.isEmpty()) {
			throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontro la pregunta frecuente");
		}	
		
		PreguntaFrecuente preguntaNueva = preguntaOpcional.get();
		
		preguntaNueva.setPregunta(preguntaFrecuente.getPregunta());
		preguntaNueva.setRespuesta(preguntaFrecuente.getRespuesta());
				
		try {
			preguntaNueva = preguntaFrecuenteRepository.save(preguntaNueva);
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {}", excepcion.getMessage() + " " + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Error al actualizar la pregunta");
		}
		
		SingleResponse<PreguntaFrecuente> response = new SingleResponse<PreguntaFrecuente>();
		response.setMensaje("La pregunta se actualizó correctamente");
		response.setOk(true);
		response.setResponse(preguntaNueva);
		return response;
	}
	
	@Override
	public SingleResponse<PreguntaFrecuente> eliminarPreguntaFrecuente(Integer idPreguntaFrecuente) {

		Optional<PreguntaFrecuente> opcionalPreguntaFrecuente = Optional.empty();
		
		try {
			opcionalPreguntaFrecuente = preguntaFrecuenteRepository.findById(idPreguntaFrecuente);						
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {}", excepcion.getMessage() + " " + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.BAD_REQUEST,"Error, no se encontro la pregunta Frecuente");
		}
		
		if(opcionalPreguntaFrecuente.isEmpty()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST,"Error, no se encontro la pregunta Frecuente");
		}
		PreguntaFrecuente preguntaFrecuente = opcionalPreguntaFrecuente.get();
		
		try {
			 preguntaFrecuenteRepository.deleteById(preguntaFrecuente.getIdPreguntaFrecuente());
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {}", excepcion.getMessage() + " " + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.BAD_REQUEST,"Error, no se pudo eliminar la pregunta Frecuente ");
		}		
		
		SingleResponse<PreguntaFrecuente> response  = new SingleResponse<PreguntaFrecuente>();
		
		response.setMensaje("La pregunta " + preguntaFrecuente.getPregunta()  + "se elimino correctamente");
		response.setOk(true);
		response.setResponse(preguntaFrecuente);
		return response;
	}

	@Override
	public SingleResponse<PreguntaFrecuente> actualizarEstatusPreguntaFrecuente(Integer idPreguntaFrecuente,Integer estatus) {
		Optional<PreguntaFrecuente> opcionalPregunta = Optional.empty();
		
		try {
			opcionalPregunta = preguntaFrecuenteRepository.findById(idPreguntaFrecuente);
			
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {}", excepcion.getMessage() + " " + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar la pregunta frecuente");
			
		}
		
		PreguntaFrecuente preguntaNuevoEstatus = opcionalPregunta.get();
		
		preguntaNuevoEstatus.setEstatus(estatus);
		
		try {
			preguntaNuevoEstatus = preguntaFrecuenteRepository.save(preguntaNuevoEstatus);
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {}", excepcion.getMessage() + " " + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,"Error al actualizar el estatus de la pregunta frecuente");
		}
		
		SingleResponse<PreguntaFrecuente> response = new SingleResponse<PreguntaFrecuente>();
		response.setMensaje("La pregunta se actualizó correctamente");
		response.setOk(true);
		response.setResponse(preguntaNuevoEstatus);
		return response;
	}

	@Override
	public SingleResponse<List<PreguntaFrecuente>> consultarPreguntasFrecuentesActivas() {
		
		List<PreguntaFrecuente> listaPregunta = new ArrayList<PreguntaFrecuente>();
		
		try {
			listaPregunta = preguntaFrecuenteRepository.findByEstatus(1);
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {}", excepcion.getMessage() + " " + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al encontrar preguntas frecuentes activas");
		}
		
		SingleResponse<List<PreguntaFrecuente>> response = new SingleResponse<List<PreguntaFrecuente>>();
	
		if(!listaPregunta.isEmpty()) {
			
			response.setMensaje("Se obtuvo las preguntas frecuentes correctamente");
			response.setOk(true);
			response.setResponse(listaPregunta);
			return response;
		}
		throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontraron preguntas activas");		
	}

	@Override
	public SingleResponse<Page<PreguntaFrecuente>> consultarPorPaginas(Integer numeroPagina, Integer tamanioPagina,
			String campo, String campoBusqueda,String direccion) {
		Pageable ordenPagina = PageRequest.of(numeroPagina -1, tamanioPagina, direccion.equalsIgnoreCase("asc") ?
		Sort.by(campo).ascending() : Sort.by(campo).descending());

		Page<PreguntaFrecuente> preguntaFrecuente = Page.empty();
		
		try {
			if(campoBusqueda.isEmpty()) {
				preguntaFrecuente = preguntaFrecuenteRepository.findAll(ordenPagina);
			}else {
				preguntaFrecuente = preguntaFrecuenteRepository.findAll(campoBusqueda,ordenPagina);
			}
			
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {}", excepcion.getMessage() + " " + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar por paginas");
		}
		
		 SingleResponse<Page<PreguntaFrecuente>> response = new  SingleResponse<Page<PreguntaFrecuente>>();
		
		if(!preguntaFrecuente.isEmpty()) {
			response.setMensaje("La consulta por preguntas se realizo correctamente");
			response.setOk(true);
			response.setResponse(preguntaFrecuente);
			return response;
		}
		throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontraron preguntas frecuentes");
		
	}

	
	
	
	 
}
