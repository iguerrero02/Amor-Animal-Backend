/**
 * 
 */
package com.example.tiendaVeterinaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.tiendaVeterinaria.model.dto.ClienteDireccion;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ClienteEntity;
import com.example.tiendaVeterinaria.model.entity.DireccionEntity;
import com.example.tiendaVeterinaria.model.entity.UsuarioEntity;
import com.example.tiendaVeterinaria.service.DireccionesServices;
import com.example.tiendaVeterinaria.service.IClienteService;

/**
 * @author ITTIVA
 * Clase encargada de manejar las rutas para manejasr los cleintes 
 */

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"enctype", "Authorization"})
public class ClientesController {
	
	@Autowired
	private IClienteService clienteService;
	
	
	@GetMapping(path = "/todos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<ClienteEntity>>> listaClientes(){
		
		SingleResponse<List<ClienteEntity>> response = new SingleResponse<>();
		response = clienteService.consultarClientes();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(path = "/consultarCliente/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<ClienteEntity>> consultarClientePorId(@PathVariable("id") Integer id){
		
		SingleResponse<ClienteEntity> response = new SingleResponse<>();
		response = clienteService.consultarClientePorId(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

	@PostMapping(path = "/nuevoCliente", consumes =MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<ClienteEntity>> crearCliente(@ModelAttribute ClienteEntity cliente){
		SingleResponse<ClienteEntity> response = new SingleResponse<>();
		response = clienteService.crearCliente(cliente);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
		
	}
	
	@DeleteMapping(path = "/eliminarCliente/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public ResponseEntity<SingleResponse<ClienteEntity>> eliminarCliente(@PathVariable Integer id) {
	    SingleResponse<ClienteEntity> response = new SingleResponse<>();
	    response = clienteService.eliminarCliente(id);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping(path = "/actualizarCliente", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<ClienteEntity>> actualizarCliente(@ModelAttribute ClienteEntity cliente) {
		SingleResponse<ClienteEntity> response = new SingleResponse<>();
		response = clienteService.actualizarCliente(cliente);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(path = "/listarClientesActivos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<ClienteEntity>>> listarUsuariosActivos(){
		SingleResponse<List<ClienteEntity>> response = new SingleResponse<>();
		response = clienteService.consultarClientesActivos();
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@GetMapping(path = "/listarClientesActivos/page/{noPagina}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<Page<ClienteEntity>>> listarActivosPorPagina(
			@PathVariable ("noPagina") Integer noPagina, @RequestParam ("campo") String campo,
			@RequestParam("direccion") String direccion,
			@RequestParam("buscar") String buscar){
		SingleResponse<Page<ClienteEntity>> response = new SingleResponse<>();
		response = clienteService.consultarPorPaginas(noPagina, campo, direccion, buscar);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@PostMapping(path = "/loginCliente", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<ClienteEntity>> loginCliente(@RequestBody ClienteEntity usuario){
		SingleResponse<ClienteEntity> response = new SingleResponse<>();
		response = clienteService.loginCliente(usuario);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}



