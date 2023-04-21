
package com.example.tiendaVeterinaria.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.example.tiendaVeterinaria.exceptions.BusinessException;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;


/**
 * Clase para manejar las excepciones genéricas
 * 
 * @Clase: HandlerExceptionController.java.
 * @version 1.0.0
 * @author Indra
 */

@ControllerAdvice
public class HandlerExceptionController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HandlerExceptionController.class);

	/**
	 * Metodo que se encarga de validar las excepciones genericas
	 * 
	 * @param ex      excepcion que se produce sin ser manejada
	 * @param request peticion realizada
	 * @return Response con respuesta estandar http
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<SingleResponse<Exception>> handleAllException(Exception ex, HttpServletRequest request) {
		LOGGER.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
				ex.getStackTrace());
		SingleResponse<Exception> response = new SingleResponse<>();
		//response.error(new ResponseCode(ResponseCodeEnums.ERROR_DESCONOCIDO), ex);
		response.setOk(false);
		response.setMensaje("Error desconocido en el servidor");
		response.setResponse(ex);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Metodo que se encarga de validar los errores en tiempo de ejecución
	 * 
	 * @param ex      Excepcion que se produce
	 * @param request peticion realizada
	 * @return Response con respuesta estandar http
	 */
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<SingleResponse<Exception>> handleRuntimeException(RuntimeException ex,
			HttpServletRequest request) {
		LOGGER.error("Ha ocurrido un error inesperado. RuntimeException {} {}",
				ex.getMessage() + " " + ex, ex.getStackTrace());
		SingleResponse<Exception> response = new SingleResponse<>();
		response.setOk(false);
		response.setMensaje("Error desconocido en el servidor");
		response.setResponse(ex);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Metodo que se encarga de validar los parametros de entrada requeridos
	 * 
	 * @param ex      Excepcion que se produce
	 * @param request peticion realizada
	 * @return Response con respuesta estandar http
	 */
	@ExceptionHandler({ MissingServletRequestParameterException.class, ServletRequestBindingException.class })
	public ResponseEntity<SingleResponse<Exception>> handleRequestParams(MissingServletRequestParameterException ex,
			HttpServletRequest request) {
		LOGGER.error("Ha ocurrido un error inesperado. MissingServletRequestParameterException {} {}",
				ex.getMessage() + " " + ex, ex.getStackTrace());
		SingleResponse<Exception> response = new SingleResponse<>();
		//response.error(new ResponseCode(ResponseCodeEnums.FALTAN_PARAMETROS), ex);
		response.setOk(false);
		response.setMensaje("Faltan Parámetros en la petición");
		response.setResponse(ex);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Metodo que se encarga de validar los errores en tiempo de ejecución
	 * 
	 * @param ex      Excepcion que se produce
	 * @param request peticion realizada
	 * @return Response con respuesta estandar http
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<SingleResponse<Exception>> handleNoHandlerFound(NoHandlerFoundException ex, WebRequest request) {
		LOGGER.error("Ha ocurrido un error inesperado. NoHandlerFoundException {} {}",
				ex.getMessage() + " " + ex, ex.getStackTrace());
		SingleResponse<Exception> response = new SingleResponse<>();
		response.setOk(false);
		response.setMensaje("Faltan Parámetros");
		response.setResponse(ex);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Metodo que se encarga de validar los parametros de entrada requeridos
	 * 
	 * @param ex      Excepcion que se produce
	 * @return Response con respuesta estandar http
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<SingleResponse<List<String>>> hanldeValidateExceptions(MethodArgumentNotValidException ex){
		SingleResponse<List<String>> response = new SingleResponse<>();
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error->
		{
			String mensaje = error.getDefaultMessage();
			errors.add(mensaje);
		});
		//response.error(new ResponseCode(ResponseCodeEnums.FALTAN_PARAMETROS, errors));
		response.setOk(false);
		response.setMensaje("Faltan Parámetros en la petición");
		response.setResponse(errors);
		
		return new ResponseEntity<> (response,HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Metodo que se encarga de validar los parametros de entrada requeridos
	 * 
	 * @param ex      Excepcion que se produce
	 * @return Response con respuesta estandar http
	 */
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<SingleResponse<String>> hanldeDataAccessException(BusinessException ex){
		SingleResponse<String> response = new SingleResponse<>();
		//response.error(ex.getResponseCode());
		response.setOk(false);
		response.setMensaje(ex.getMensaje());
		//response.setResponse(ex);
		
		return new ResponseEntity<> (response, ex.getHttpEstatus());
	}
	

}
