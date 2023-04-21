package com.example.tiendaVeterinaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.CategoriasEntity;
import com.example.tiendaVeterinaria.service.ICategoriaService;

@CrossOrigin("*")
@RestController
@RequestMapping("/categorias")
public class CategoriasController {

	@Autowired
	private ICategoriaService categoriaService;
	
	
	@GetMapping(path = "/todos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<CategoriasEntity>>> consultarCategorias(){
		SingleResponse<List<CategoriasEntity>> response = new SingleResponse<List<CategoriasEntity>>();
		response = categoriaService.consultarCategorias();
		return new ResponseEntity<SingleResponse<List<CategoriasEntity>>>(response,HttpStatus.OK);
	}
	
	@PostMapping(path = "/guardarCategoria", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<CategoriasEntity>> guardarCategoria(@RequestBody CategoriasEntity categoria){
		SingleResponse<CategoriasEntity> response = new SingleResponse<CategoriasEntity>();
		response = categoriaService.guardarCategoria(categoria);
		return new ResponseEntity<SingleResponse<CategoriasEntity>>(response,HttpStatus.OK);
	}
	
	@PutMapping(value = "/actualizarCategoria", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<CategoriasEntity>> actualizarCategoria(@RequestBody CategoriasEntity categoria){
		SingleResponse<CategoriasEntity> response = new SingleResponse<CategoriasEntity>();
		response = categoriaService.actualizarCategoria(categoria);
		return new ResponseEntity<SingleResponse<CategoriasEntity>>(response,HttpStatus.OK);
	}
	
	@PutMapping(value = "/actualizarEstatusCategoria", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<CategoriasEntity>>actualizarEstatusCategoria(@RequestParam Integer idCategoria, @RequestParam Integer estatus){
		SingleResponse<CategoriasEntity> response = new SingleResponse<CategoriasEntity>();
		response = categoriaService.actualizarEstatusCategoria(idCategoria,estatus);
		return new ResponseEntity<SingleResponse<CategoriasEntity>>(response,HttpStatus.OK);
	}
	
	@GetMapping(value = "/consultarCategoriasActivas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<CategoriasEntity>>> consultarCategoriasActivas(){
		SingleResponse<List<CategoriasEntity>> response = new SingleResponse <List<CategoriasEntity>>();
		response = categoriaService.consultarCategoriasActivas();
		return new ResponseEntity<SingleResponse<List<CategoriasEntity>>>(response,HttpStatus.OK);		
		
	}
	
	@GetMapping(value = "/consultarCategoriasPorPagina/pagina/{numeroPagina}/{tamanioPagina}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<Page<CategoriasEntity>>> consultarCategoriaPorPaginas(@PathVariable ("numeroPagina") Integer numeroPagina,
			@PathVariable ("tamanioPagina") Integer tamanioPagina,@RequestParam String campo,@RequestParam String campoBusqueda, @RequestParam String direccion){
		SingleResponse<Page<CategoriasEntity>> response = categoriaService.consultarPorPaginas(numeroPagina, tamanioPagina, campo, campoBusqueda, direccion);
		return new ResponseEntity<SingleResponse<Page<CategoriasEntity>>>(response,HttpStatus.OK);		
	}
	
	@PostMapping(value = "/consultarCategoriasIdPadre", produces = MediaType.APPLICATION_JSON_VALUE)
	//requiered false si la categoria tiene null, no manda el error si no se envia el parametro
	public ResponseEntity<SingleResponse<List<CategoriasEntity>>> consultarPorIdPadre(@RequestParam(required = false) Integer idPadre){
		SingleResponse<List<CategoriasEntity>> response =  categoriaService.consultarPorIdCategoriaPadre(idPadre);
		return new ResponseEntity<SingleResponse<List<CategoriasEntity>>>(response,HttpStatus.OK);			
	}
	
	@GetMapping(value = "/consultarCategoriasPadre",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<CategoriasEntity>>> consultarCategoriaPadre(){
		SingleResponse<List<CategoriasEntity>> response = categoriaService.consultarCategoriasPadres();
		return new ResponseEntity<SingleResponse<List<CategoriasEntity>>>(response,HttpStatus.OK);
	}
	
	
}
