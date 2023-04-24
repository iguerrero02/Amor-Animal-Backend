package com.example.tiendaVeterinaria.controller;

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

import com.example.tiendaVeterinaria.model.dto.CrearOrdenRequest;
import com.example.tiendaVeterinaria.model.dto.OrdenActualizarDTO;
import com.example.tiendaVeterinaria.model.dto.OrdenDetalleAgregarProductoDTO;
import com.example.tiendaVeterinaria.model.dto.OrdenDto;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.OrdenDetalleEntity;
import com.example.tiendaVeterinaria.model.entity.OrdenesEntity;
import com.example.tiendaVeterinaria.service.IOrdenesService;

@RestController
@RequestMapping("/ordenes")
@CrossOrigin(origins = "*", allowedHeaders = {"enctype", "Authorization"})
public class OrdenesController {
	
	@Autowired
	private IOrdenesService ordenesService;
	
	@GetMapping(path = "/listarOrdenesPorPagina/page/{noPagina}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<Page<OrdenesEntity>>> listarOrdenesPorPagina(
			@PathVariable ("noPagina") Integer noPagina, @RequestParam ("campo") String campo,
			@RequestParam("direccion") String direccion,
			@RequestParam("buscar") String buscar){
		SingleResponse<Page<OrdenesEntity>> response = new SingleResponse<>();
		response = ordenesService.consultarPorPaginas(noPagina, campo, direccion, buscar);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	
	@GetMapping(path = "/detalleOrden/{idOrden}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<OrdenDto>> detalleUsuario(@PathVariable ("idOrden") Integer idOrden){
		SingleResponse<OrdenDto> response = new SingleResponse<>();
		response = ordenesService.detalleOrden(idOrden);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(path = "/crearOrden", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<OrdenDto>> crearOrden(@RequestBody CrearOrdenRequest request){
		SingleResponse<OrdenDto> response = new SingleResponse<>();
		response = ordenesService.crearOrden(request);
		return new ResponseEntity<>(response, HttpStatus.OK); 	 //Se crea respuesta Ok
	}
	@PutMapping(path = "/actualizar",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<OrdenesEntity>> actualizarOrden(@RequestBody OrdenActualizarDTO ordenActualizar){
		SingleResponse<OrdenesEntity> response = ordenesService.actualizarOrden(ordenActualizar);
		return new ResponseEntity<SingleResponse<OrdenesEntity>>(response,HttpStatus.OK);
	}
	
	@PutMapping(path = "/agregarProductoOrdenDetalle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<OrdenDetalleEntity>> agregarProductoOrdenDetalle(@RequestBody OrdenDetalleAgregarProductoDTO ordenDetalle){
		SingleResponse<OrdenDetalleEntity> response = ordenesService.agregarProductoOrdenDetalle(ordenDetalle);
		return new ResponseEntity<SingleResponse<OrdenDetalleEntity>>(response,HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/eliminarProductoOrdenDetalle",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<OrdenDetalleEntity>> eliminarProductoOrdenDetalle(@RequestParam Integer idOrden,
			@RequestParam Integer idOrdenDetalle,@RequestParam Double iva){
		SingleResponse<OrdenDetalleEntity> response = ordenesService.eliminarProductoOrdenDetalle(idOrden, idOrdenDetalle, iva);
		return new ResponseEntity<SingleResponse<OrdenDetalleEntity>>(response, HttpStatus.OK);				
	}
	
	@DeleteMapping(path = "/eliminarOrden",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<OrdenesEntity>> eliminarOrden(@RequestParam Integer idOrden){
		SingleResponse<OrdenesEntity> response = ordenesService.eliminarOrden(idOrden);
		return new ResponseEntity<SingleResponse<OrdenesEntity>>(response,HttpStatus.OK);
	}
	
	

}
