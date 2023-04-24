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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.UsuarioEntity;
import com.example.tiendaVeterinaria.service.IUsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = {"enctype", "Authorization"})
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping(path = "/todos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<UsuarioEntity>>> listarUsuarios(){
		SingleResponse<List<UsuarioEntity>> response = new SingleResponse<>();
		response = usuarioService.consultarUsuarios();
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@PostMapping(path = "/nuevoUsuario", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<UsuarioEntity>> nuevoUsuario(@ModelAttribute UsuarioEntity usuario){
		SingleResponse<UsuarioEntity> response = new SingleResponse<>();
		response = usuarioService.crearUsuario(usuario);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}

	@PutMapping(path = "/actualizarUsuario", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<UsuarioEntity>> actualizarUsuario(@ModelAttribute UsuarioEntity usuario){
		SingleResponse<UsuarioEntity> response = new SingleResponse<>();
		response = usuarioService.actualizarUsuario(usuario);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/eliminarUsuario/{idUsuario}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<UsuarioEntity>> eliminarUsuario(@PathVariable ("idUsuario") Integer idUsuario){
		SingleResponse<UsuarioEntity> response = new SingleResponse<>();
		response = usuarioService.eliminarUsuario(idUsuario);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(path = "/listarUsuariosActivos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<UsuarioEntity>>> listarUsuariosActivos(){
		SingleResponse<List<UsuarioEntity>> response = new SingleResponse<>();
		response = usuarioService.consultarUsuariosActivos();
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@GetMapping(path = "/listarUsuariosActivos/page/{noPagina}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<Page<UsuarioEntity>>> listarActivosPorPagina(
			@PathVariable ("noPagina") Integer noPagina, @RequestParam ("campo") String campo,
			@RequestParam("direccion") String direccion,
			@RequestParam("buscar") String buscar){
		SingleResponse<Page<UsuarioEntity>> response = new SingleResponse<>();
		response = usuarioService.consultarPorPaginas(noPagina, campo, direccion, buscar);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@GetMapping(path = "/detalleUsuario/{idUsuario}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<UsuarioEntity>> detalleUsuario(@PathVariable ("idUsuario") Integer idUsuario){
		SingleResponse<UsuarioEntity> response = new SingleResponse<>();
		response = usuarioService.detalleUsuario(idUsuario);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(path = "/loginUsuario", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<UsuarioEntity>> loginUsuario(@RequestBody UsuarioEntity usuario){
		SingleResponse<UsuarioEntity> response = new SingleResponse<>();
		response = usuarioService.loginUsuario(usuario);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
