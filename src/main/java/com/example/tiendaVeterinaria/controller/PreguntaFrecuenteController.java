package com.example.tiendaVeterinaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.PreguntaFrecuente;
import com.example.tiendaVeterinaria.service.IPreguntaFrecuenteService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = {"enctype", "Authorization"})
@RequestMapping("/preguntaFrecuente")
public class PreguntaFrecuenteController {

	@Autowired
	private IPreguntaFrecuenteService preguntaService;
	
	@GetMapping(value = "/consultarTodos",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<PreguntaFrecuente>>> consultarTodos(){
		SingleResponse<List<PreguntaFrecuente>> response = preguntaService.consultaPreguntaFrecuentes();
		return new ResponseEntity<SingleResponse<List<PreguntaFrecuente>>>(response,HttpStatus.OK);
	}
	
	@PostMapping(value = "/guardar",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<PreguntaFrecuente>>guardar(@RequestBody PreguntaFrecuente pregunta){
		SingleResponse<PreguntaFrecuente> response = preguntaService.guardarPreguntaFrecuente(pregunta);
		return new ResponseEntity<SingleResponse<PreguntaFrecuente>>(response,HttpStatus.OK);		
	}
	
	@PutMapping(value = "/actualizar",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<PreguntaFrecuente>>actualizarPregunta(@RequestBody PreguntaFrecuente pregunta){
		SingleResponse<PreguntaFrecuente> response = preguntaService.actualizarPreguntaFrecuente(pregunta);
		return new ResponseEntity<SingleResponse<PreguntaFrecuente>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/eliminar",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<PreguntaFrecuente>> eliminarPregunta(@RequestParam Integer idPreguntaFrecuente){
		SingleResponse<PreguntaFrecuente> response = preguntaService.eliminarPreguntaFrecuente(idPreguntaFrecuente);
		return new ResponseEntity<SingleResponse<PreguntaFrecuente>>(response,HttpStatus.OK);
	}
	
	
	@PutMapping(value = "/actualizarEstatus",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<PreguntaFrecuente>> actualizarEstatusPregunta(@RequestParam Integer idPreguntaFrecuente,@RequestParam Integer estatus){
		SingleResponse<PreguntaFrecuente> response = preguntaService.actualizarEstatusPreguntaFrecuente(idPreguntaFrecuente, estatus);
		return new ResponseEntity<SingleResponse<PreguntaFrecuente>>(response, HttpStatus.OK);
			
	}	
	
	@GetMapping(value = "/consultarTodosActivos",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<PreguntaFrecuente>>> consultarTodosActivos(){
		SingleResponse<List<PreguntaFrecuente>> response = preguntaService.consultarPreguntasFrecuentesActivas();
		return new ResponseEntity <SingleResponse<List<PreguntaFrecuente>>>(response,HttpStatus.OK);
	}
	
	@GetMapping(value = "/consultarPorPagina")
	public ResponseEntity<SingleResponse<Page<PreguntaFrecuente>>> consultarTodosPagina(@PathVariable Integer numeroPagina,
		@PathVariable Integer tamanioPagina,@PathVariable String campo,@PathVariable String direccion){
		SingleResponse<Page<PreguntaFrecuente>> response = preguntaService.consultarPorPaginas(numeroPagina, tamanioPagina, campo, campo, direccion);
		return new ResponseEntity<SingleResponse<Page<PreguntaFrecuente>>>(response,HttpStatus.OK);
	}
	
}
