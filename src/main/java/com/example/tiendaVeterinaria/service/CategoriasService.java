package com.example.tiendaVeterinaria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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

import com.example.tiendaVeterinaria.exceptions.BusinessException;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.CategoriasEntity;
import com.example.tiendaVeterinaria.repository.ICategoriaRepository;

@Service
public class CategoriasService implements ICategoriaService {

	private static final Logger log = LoggerFactory.getLogger(CategoriasService.class);
	private static final Integer ESTATUS = 1;
	
	@Autowired
	private ICategoriaRepository categoriaRepository;
	
	@Override
	public SingleResponse<List<CategoriasEntity>> consultarCategorias() {
		List<CategoriasEntity> listaCategorias = new ArrayList<CategoriasEntity>();
		try {
			listaCategorias = categoriaRepository.findAll();			
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {}");
			excepcion.getStackTrace();
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,"Error al consultar las categorias en la base de datos");
		}
		if(!listaCategorias.isEmpty()) {
			SingleResponse<List<CategoriasEntity>> response = new SingleResponse<List<CategoriasEntity>>();
			response.setOk(true);
			response.setMensaje("Se obtuvo las categorias con exito");
			response.setResponse(listaCategorias);
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron categorias en la Base de datos");
		
	}

	@Transactional
	@Override
	public SingleResponse<CategoriasEntity> guardarCategoria(CategoriasEntity categoria) {
		Optional<CategoriasEntity> categoriaOpcional = Optional.empty();
		
		try {
			categoriaOpcional = categoriaRepository.findByNombreCategoriaIgnoreCase(categoria.getNombreCategoria());
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado {} {} ",excepcion.getMessage() + " " + excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar las categorias en la BD");		
		}
		if(categoriaOpcional.isPresent()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "La categoria " + categoria.getNombreCategoria() + "ya existe en la BD");
		}
		categoria.setEstatus(1);
		try {
			 categoria = categoriaRepository.save(categoria);
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {}",excepcion.getMessage() + "" + excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar la categoria en la Base de Datos");
		}
		SingleResponse<CategoriasEntity> response = new SingleResponse<CategoriasEntity>();
		response.setOk(true);
		response.setMensaje("Se ha guardado la categoria" + categoria.getNombreCategoria() + "correctamente");
		response.setResponse(categoria);		
		return response;
	}

	@Override
	public SingleResponse<CategoriasEntity> actualizarCategoria(CategoriasEntity categoria) {
		
		Optional<CategoriasEntity> categoriaOpcional = Optional.empty();
		
		try {
			categoriaOpcional = categoriaRepository.findById(categoria.getIdCategorias());	
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {}", excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al encontrar la categoria en la Base de Datos");
		}
		
		if(!categoriaOpcional.isPresent()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "La categoria con ID" + categoria.getIdCategorias() + "no se encontro en la base de datos");
		}
		
		
		CategoriasEntity categoriaActualizado = categoriaOpcional.get();					
		categoriaActualizado.setCodigoReferencia(categoria.getCodigoReferencia());
		categoriaActualizado.setDescripcionCategoria(categoria.getDescripcionCategoria());
		categoriaActualizado.setDetallesCategoria(categoria.getDetallesCategoria());
		categoriaActualizado.setEstatus(categoria.getEstatus());
		categoriaActualizado.setIdCategoriaPadre(categoria.getIdCategoriaPadre());
		categoriaActualizado.setNombreCategoria(categoria.getNombreCategoria());	
		try {
			categoriaActualizado = categoriaRepository.save(categoriaActualizado);						
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error Inesperado Excepcion {} {} ", excepcion.getMessage() + "" , excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar la categoria");
		}
		
		SingleResponse<CategoriasEntity> response =  new SingleResponse<CategoriasEntity>();
		response.setOk(true);
		response.setMensaje("La categoria se ha actualizado");
		response.setResponse(categoriaActualizado);
		
		return response;
	}

	@Override
	public SingleResponse<CategoriasEntity> actualizarEstatusCategoria(Integer idCategoria, Integer estatus) {
		
		Optional<CategoriasEntity> categoriaOpcional = Optional.empty();
							
		try {
			categoriaOpcional = categoriaRepository.findById(idCategoria);
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {}", excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,"Error al encontrar la categoria en la Base de Datos");
		}
		
		if(categoriaOpcional.isEmpty()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "La Categoria con el ID: "+ idCategoria + "no se encontro");
		}
				
		CategoriasEntity categoriaBorrado = categoriaOpcional.get();
		categoriaBorrado.setEstatus(estatus);
		
		try {
			categoriaBorrado = categoriaRepository.save(categoriaBorrado);			
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Excepcion {} {}", excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la categoria");
		}
		
		SingleResponse<CategoriasEntity> response = new SingleResponse<CategoriasEntity>();
		
		response.setMensaje("El estatus de la categoria se actualizo correctamente");
		response.setOk(true);
		response.setResponse(categoriaBorrado);
		return response;
	}

	@Override
	public SingleResponse<List<CategoriasEntity>> consultarCategoriasActivas() {
		List<CategoriasEntity> listaCategoria = new ArrayList<CategoriasEntity>();
		try {
			listaCategoria = categoriaRepository.findByEstatus(ESTATUS);		
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado {} {} ",excepcion.getMessage() + " " + excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar las categorias en la BD");		
		}
		
		if(!listaCategoria.isEmpty()) {
			SingleResponse<List<CategoriasEntity>> response = new SingleResponse<List<CategoriasEntity>>();
			response.setOk(true);
			response.setMensaje("Se obtuvo las categorias con exito");
			response.setResponse(listaCategoria);
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron categorias en la Base de datos");
				
	}

	@Override
	public SingleResponse<Page<CategoriasEntity>> consultarPorPaginas(Integer numeroPagina, Integer tamanioPagina, 
			String campo,String campoBusqueda,
			String direccion) {
		log.info(campo);
		//Mandar un asc en el campo direccion para que lo ordene ascndentde si manda otro se orderna de maneras descendente
		//numero pagina se pone -1 por que si se manda un uno no hace la paginacion
		Pageable pageable = PageRequest.of(numeroPagina -1, tamanioPagina,direccion.equalsIgnoreCase("asc") 
				? Sort.by(campo).ascending() : Sort.by(campo).descending());
		
		Page<CategoriasEntity> categoriaPage = Page.empty();
		
		try {
			
			if(!campoBusqueda.isEmpty()) {				
				categoriaPage = categoriaRepository.findAll(campoBusqueda,pageable);
			}else {
				categoriaPage = categoriaRepository.findAll(pageable);
			}
			
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar las categorias en la BD");
		}
		
		if(!categoriaPage.isEmpty()) {
			SingleResponse<Page<CategoriasEntity>> response = new SingleResponse<Page<CategoriasEntity>>();
			response.setMensaje("Se ha obtenido la lista correctamente");
			response.setOk(true);
			response.setResponse(categoriaPage);
			return response;

		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registros en la página " + numeroPagina);

		
	}

	@Override
	public SingleResponse<List<CategoriasEntity>> consultarPorIdCategoriaPadre(Integer idPadre) {
		
		SingleResponse<List<CategoriasEntity>> response = new SingleResponse<List<CategoriasEntity>>();
		List<CategoriasEntity>  listaCategoria = new ArrayList<CategoriasEntity>();			
		
		try {			
			listaCategoria = categoriaRepository.findByIdCategoriaPadre(idPadre);	
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar las categorias en la BD");
			// TODO: handle exception
		}
		
		if(!listaCategoria.isEmpty()) {
			response.setMensaje("Las categorias del padre son las siguientes");
			response.setResponse(listaCategoria);
			response.setOk(true);
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registron en la categoria padre");
		 
	}

	@Override
	public SingleResponse<List<CategoriasEntity>> consultarCategoriasPadres() {

		List<CategoriasEntity> listaCategoriaPadre = new ArrayList<CategoriasEntity>();
		
		try {
			listaCategoriaPadre = categoriaRepository.findByIdCategoriaPadre(); 			
		} catch (DataAccessException excepcion) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", excepcion.getMessage() + " " + excepcion,
					excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Error al consiltar las categorias padre");
		}
		
		SingleResponse<List<CategoriasEntity>> response = new SingleResponse<List<CategoriasEntity>>();
		
		if(!listaCategoriaPadre.isEmpty()) {
			response.setMensaje("Las categorias del padre son las siguientes");
			response.setOk(true);
			response.setResponse(listaCategoriaPadre);
			return response;
		}
		
		throw new BusinessException(HttpStatus.NOT_FOUND,"Datos no encontrados");
		
	}

}
