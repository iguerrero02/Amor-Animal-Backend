package com.example.tiendaVeterinaria.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ImagenBannerEntity;
import com.example.tiendaVeterinaria.service.IImagenBannerService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"enctype", "Authorization"})
@RequestMapping("/imagenbanner")
public class ImagenBannerController {

	@Autowired
	private IImagenBannerService imagenBannerService;
	
	@GetMapping(value = "/todos",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<ImagenBannerEntity>>> consultarTodos(){
		SingleResponse<List<ImagenBannerEntity>> response = imagenBannerService.consultarTodosImagenBanner();
		return new ResponseEntity<SingleResponse<List<ImagenBannerEntity>>>(response,HttpStatus.OK);
	}
	
	@PostMapping(value = "/guardar",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	//@RequestParam(required = false) se ponse porque lo que queremos es que no mande un error en spring en el service tenemos
	// la validacion si es == null mandar un mensaje el usuario si ponemos que es requirido spring mandar un error	
	//	La anotación @Valid se utiliza en Spring para validar automáticamente el objeto que se pasa como argumento en un método de controlador utilizando las restricciones de validación definidas en su entidad correspondiente.
	//NOTA: al enviarlo al back tiene que ser con el mismo nombre que es imagen	
	public ResponseEntity<SingleResponse<ImagenBannerEntity>>guardarImagenBanner(@Valid ImagenBannerEntity imagenBanner,@RequestParam(required = false) MultipartFile imagen) throws IOException{
		SingleResponse<ImagenBannerEntity> response = imagenBannerService.guardarImagenBanner(imagenBanner, imagen);
		return new ResponseEntity<SingleResponse<ImagenBannerEntity>>(response,HttpStatus.OK);
	}
	
	@PutMapping(value = "/actualizar",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<ImagenBannerEntity>> actualizarImagenBanner(@Valid ImagenBannerEntity imagenBanner, @RequestParam(required = false) MultipartFile imagen) throws IOException{
		SingleResponse<ImagenBannerEntity> response = imagenBannerService.actualizarImagenBanner(imagenBanner,imagen);
		return new ResponseEntity<SingleResponse<ImagenBannerEntity>>(response,HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/eliminar",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<ImagenBannerEntity>>eliminarImagenBanner(@RequestParam Integer idImagenBanner){
		SingleResponse<ImagenBannerEntity> response = imagenBannerService.eliminarImagenBanner(idImagenBanner);
		return new ResponseEntity<SingleResponse<ImagenBannerEntity>>(response, HttpStatus.OK);
		
	}
	
	@PutMapping(value = "/actualizarEstatus",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<ImagenBannerEntity>>actualizarEstatusImagenBanner(@RequestParam Integer idImagenBanner, @RequestParam Integer estatus){
		SingleResponse<ImagenBannerEntity> response = imagenBannerService.actualizarEstatusImagenBanner(idImagenBanner, estatus);
		return new ResponseEntity<SingleResponse<ImagenBannerEntity>>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/todosActivos",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SingleResponse<List<ImagenBannerEntity>>>consultarTodosActivosImagenBanner(){
		SingleResponse<List<ImagenBannerEntity>> response = imagenBannerService.consultarTodosActivosImagenBanner();
		return new ResponseEntity<SingleResponse<List<ImagenBannerEntity>>>(response,HttpStatus.OK);
	}
	
}