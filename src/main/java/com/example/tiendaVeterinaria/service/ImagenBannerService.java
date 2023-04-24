package com.example.tiendaVeterinaria.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.tiendaVeterinaria.exceptions.BusinessException;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ImagenBannerEntity;
import com.example.tiendaVeterinaria.repository.IImagenBannerRepository;

@Service
public class ImagenBannerService implements IImagenBannerService {
	
	private static final Logger log = LoggerFactory.getLogger(ImagenBannerService.class);
	
	@Autowired
	private IImagenBannerRepository imagenBannerRepository;
	

	@Override
	public SingleResponse<List<ImagenBannerEntity>> consultarTodosImagenBanner() {
		List<ImagenBannerEntity> listaImagenesBanners = new ArrayList<ImagenBannerEntity>(); 
		
		try {
			listaImagenesBanners = imagenBannerRepository.findAll();
		} catch (DataAccessException excepcion) {
			log.error("Ha occurido un error inesperado. Excepcion {} {} ",excepcion.getMessage()
					+ "" + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los banners en la base de datos");
		}
		
		SingleResponse<List<ImagenBannerEntity>> response = new SingleResponse<List<ImagenBannerEntity>>();
		if(!listaImagenesBanners.isEmpty()) {
			response.setMensaje("Los Banners se consultaron exitosamente");
			response.setOk(true);
			response.setResponse(listaImagenesBanners);			
			return response;
		}
		throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontrar banners en la base de datos");		
	}

	@Override
	public SingleResponse<ImagenBannerEntity> guardarImagenBanner(ImagenBannerEntity imagenBanner, MultipartFile imagen) throws IOException {

		if(imagen == null) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "La imagen es obligatoria");
		}
		
		imagenBanner.setImagenBits(imagen.getBytes());
		imagenBanner.setNombreArchivo(imagen.getOriginalFilename());
		imagenBanner.setTipoArchivo(imagen.getContentType());
		imagenBanner.setEstatus(1);
		
		try {
			imagenBanner = imagenBannerRepository.save(imagenBanner);
			
		} catch (DataAccessException excepcion) {
			log.error("Ha occurido un error inesperado. Excepcion {} {} ",excepcion.getMessage()
					+ "" + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,"Error al guardar el banner en la base de datos");
		}
		
		SingleResponse<ImagenBannerEntity> response = new SingleResponse<ImagenBannerEntity>();
		
		response.setMensaje("El banner ser guardo correctamente");
		response.setOk(true);
		response.setResponse(imagenBanner);
		return response;
	}

	@Override
	public SingleResponse<ImagenBannerEntity> actualizarImagenBanner(ImagenBannerEntity imagenBannerNueva, MultipartFile imagen)throws IOException {
		Optional<ImagenBannerEntity> imagenOpcional = Optional.empty();
				
		try {				
			imagenOpcional = imagenBannerRepository.findById(imagenBannerNueva.getIdBanner());			
		} catch (DataAccessException excepcion) {
			log.error("Ha occurido un error inesperado. Excepcion {} {} ",excepcion.getMessage()
					+ "" + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar la imagen por id");
		}
		if(imagenOpcional.isEmpty()) {
			throw new BusinessException(HttpStatus.NOT_FOUND, "Error al encontrar en la imagen");
		}
		
		ImagenBannerEntity imagenBannerNueva2 = imagenOpcional.get();
		if(imagen != null) {			
			imagenBannerNueva2.setImagenBits(imagen.getBytes());
			imagenBannerNueva2.setNombreArchivo(imagen.getOriginalFilename());
			imagenBannerNueva2.setTipoArchivo(imagen.getContentType());
			imagenBannerNueva2.setDescripcion(imagenBannerNueva.getDescripcion());
			imagenBannerNueva2.setEstatus(imagenBannerNueva.getEstatus());
		}
		if(imagen == null) {
			imagenBannerNueva2.setDescripcion(imagenBannerNueva.getDescripcion());
			imagenBannerNueva2.setEstatus(imagenBannerNueva.getEstatus());		
		}
		
		try {
			imagenBannerNueva2 = imagenBannerRepository.save(imagenBannerNueva2);
		} catch (DataAccessException excepcion) {
			log.error("Ha occurido un error inesperado. Excepcion {} {} ",excepcion.getMessage()
					+ "" + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar la imagen");
		}
		
		SingleResponse<ImagenBannerEntity> response = new SingleResponse<ImagenBannerEntity>();
		
		response.setMensaje("El banner se actualizó correctamete");
		response.setOk(true);
		response.setResponse(imagenBannerNueva2);
			
		return response;
	}

	@Override
	public SingleResponse<ImagenBannerEntity> eliminarImagenBanner(Integer idImagenBanner) {
		
		Optional<ImagenBannerEntity> imagenOpcional = Optional.empty();
		
		try {
			imagenOpcional = imagenBannerRepository.findById(idImagenBanner);
		} catch (DataAccessException excepcion) {
			log.error("Ha occurido un error inesperado. Excepcion {} {} ",excepcion.getMessage()
					+ "" + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar el baner ");
		}
		
		if(imagenOpcional.isEmpty()) {
			throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontro el banner");
		}
		
		ImagenBannerEntity imagenEliminar = imagenOpcional.get();
		
		try {
			imagenBannerRepository.deleteById(imagenEliminar.getIdBanner());
		} catch (DataAccessException excepcion) {
			log.error("Ha occurido un error inesperado. Excepcion {} {} ",excepcion.getMessage()
					+ "" + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "No se eliminó el banner en la base de datos ");
		}
		
		SingleResponse<ImagenBannerEntity> response = new SingleResponse<ImagenBannerEntity>();
		response.setOk(true);
		response.setMensaje("El banner se elimino exitosamente");
		response.setResponse(imagenEliminar);		
		return response;
	}

	@Override
	public SingleResponse<ImagenBannerEntity> actualizarEstatusImagenBanner(Integer idImagenBanner, Integer estatus) {
		Optional<ImagenBannerEntity> opcionImagen = Optional.empty();
		
		try {
			opcionImagen = imagenBannerRepository.findById(idImagenBanner);
		} catch (DataAccessException excepcion) {
			log.error("Ha occurido un error inesperado. Excepcion {} {} ",excepcion.getMessage()
					+ "" + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.NOT_FOUND, "No se encontro el baner en la base de datos ");
		}
		
		ImagenBannerEntity imagenBannerNuevoEstatus = opcionImagen.get();
		imagenBannerNuevoEstatus.setEstatus(estatus);
		
		try {
			imagenBannerNuevoEstatus = imagenBannerRepository.save(imagenBannerNuevoEstatus);
		} catch (DataAccessException excepcion) {
			log.error("Ha occurido un error inesperado. Excepcion {} {} ",excepcion.getMessage()
					+ "" + excepcion.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "No se actualizo banner ");
		}
				
		SingleResponse<ImagenBannerEntity> response = new SingleResponse<ImagenBannerEntity>();
		
		response.setMensaje("El estatus del banner se actualizó correctamente");
		response.setOk(true);
		response.setResponse(imagenBannerNuevoEstatus);
		return response;
	}

	@Override
	public SingleResponse<List<ImagenBannerEntity>> consultarTodosActivosImagenBanner() {
		List<ImagenBannerEntity> listaImagenBanners = new ArrayList<ImagenBannerEntity>();		
		
		try {
			listaImagenBanners = imagenBannerRepository.findByEstatus(1);
		} catch (DataAccessException excepcion) {
			log.error("Ha occurido un error inesperado. Excepcion {} {} ",excepcion.getMessage()
					+ "" + excepcion.getStackTrace());		
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo consultar banners en la base de datos ");

		}
		SingleResponse<List<ImagenBannerEntity>> response = new SingleResponse<List<ImagenBannerEntity>>();
		
		if(!listaImagenBanners.isEmpty()) {
			response.setResponse(listaImagenBanners);
			response.setOk(true);
			response.setMensaje("Los Banners se consultaron correctamente");
			return response;
		}
		throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "No se encontraron banners activos en la base de datos");
				
	}	
		
	

}
