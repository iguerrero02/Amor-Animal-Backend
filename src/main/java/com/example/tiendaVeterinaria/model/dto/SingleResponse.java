package com.example.tiendaVeterinaria.model.dto;

import java.io.Serializable;
import java.util.List;

import com.example.tiendaVeterinaria.model.entity.ClienteEntity;
import com.example.tiendaVeterinaria.model.entity.DireccionEntity;

import lombok.Getter;
import lombok.Setter;


/**
 * Clase para contener la respuesta genérica de todos 
 * los Endpoints
 * 
 * @Clase: SingleResponse.java. <br/>
 * @param <R> Tipo de la respuesta
 * 
 * @version 1.0.0
 */
@Getter
@Setter
public class SingleResponse<R> implements Serializable {
	/**
	 * Numero de serializacion
	 */
	private static final long serialVersionUID = -8083211585525204680L;


	/**
	 * Codigo de respuesta emitido
	 */
//	private ResponseCode responseCode;
	
	private String mensaje;

	/**
	 * Objeto de la respuesta
	 */
	private transient R response;
	
	/**
	 * Determina la respuesta es correcta(true) o contiene errores o
	 * validaciones(false)
	 */
	private boolean ok = false;


	/**
	 * Constructor de la clase
	 */
	public SingleResponse() {
		super();
	}


	

	/**
	 * Metodo que ingresa a la respuesta la lista de validaciones, este metodo
	 * determinara que la respuesta no es correcta y que deben revisarse los
	 * elementos
	 * 
	 * @param validationsCodes lista de validaciones
	 */
//	public void validations(final List<ResponseCode> validationsCodes) {
//		this.response = null;
//		this.ok = false;
//	}

	/**
	 * Metodo que determina que la respuesta es exitosa y su mensaje, puede estar
	 * con formato {} para usar los parametros en la cadena
	 * 
	 * @param response     Objeto de respuesta, es obligatorio almenos un true o
	 *                     string
	 * @param responseCode mensaje de exito de la respuesta. Ej. "Operacion
	 *                     realizada exitosamente."
	 * @param params       parametros para el mensaje.
	 */
//	public void done(final R response, final ResponseCode responseCode, final Object... params) {
//		this.response = response;
//		this.ok = true;
//		responseCode.setMessage(Util.formatMsg(responseCode.getMessage(), params));
//		this.responseCode = responseCode;
//	}

	/**
	 * Metodo que determina que la respuesta es incorrecta. El mensaje se determina
	 * en el metodo getMessage() de Exception o su implementacion
	 * 
	 * @param responseCode codigo de respuesta de la operacion
	 * @param exception    excepcion que provoco el error
	 * @param params       parametros a colocar en el mensaje de error
	 */
//	public void error(final ResponseCode responseCode, final Exception exception, final Object... params) {
//		this.ok = false;
//		this.response = null;
//		this.responseCode = responseCode;
//	}


	/**
	 * Metodo que determina que la respuesta es incorrecta. El mensaje se determina
	 * en el metodo getMessage() de Exception o su implementacion
	 * 
	 * @param responseCode codigo de respuesta de la operacion
	 * @param params       parametros a colocar en el mensaje de error
	 */
//	public void error(final ResponseCode responseCode, final Object... params) {
//		this.ok = false;
//		this.response = null;
//		responseCode.setMessage(Util.formatMsg(responseCode.getMessage(), params));
//		this.responseCode = responseCode;
//	}

}
