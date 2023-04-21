package com.example.tiendaVeterinaria.service;

import java.io.Console;
import java.io.IOException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tiendaVeterinaria.exceptions.BusinessException;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ClienteEntity;
import com.example.tiendaVeterinaria.model.entity.UsuarioEntity;
import com.example.tiendaVeterinaria.repository.IClienteRepository;
import com.example.tiendaVeterinaria.repository.IUsuarioRepository;

import net.bytebuddy.utility.RandomString;

import net.bytebuddy.utility.RandomString;

@Service
public class RegistroService implements IRegistroService {

	public static final Integer ESTATUS_ACTIVO = 1;
	public static final Integer ESTATUS_INACTIVO = 0;
	private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IClienteRepository clienteRepository;
	
	@Override
	public SingleResponse<ClienteEntity> confirmarCodigo(ClienteEntity clienteDireccion) throws MessagingException {
		Optional<ClienteEntity> clienetO = Optional.empty();
		try {
			clienetO = clienteRepository.findByClienteCodigo(clienteDireccion.getCodigoVerificacion());
		} catch (Exception ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error alcambiar la contraseña en la BD");
		}
		
		SingleResponse<ClienteEntity> response = new SingleResponse<>();
		response.setOk(true);
		response.setMensaje("Se ha guardado al cliente ");
		response.setResponse(clienetO.get());
		return response;
	}

	@Override
	public SingleResponse<ClienteEntity> resetPassword(ClienteEntity clienteDireccion) throws MessagingException {
		Optional<ClienteEntity> clienteO = Optional.empty();

		try {
			clienteO = clienteRepository.findBycorreoElectronicoIgnoreCase(clienteDireccion.getCorreoElectronico());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los usuarios en la BD");
		}

		if (!clienteO.isPresent()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST,
					"El  correo electrónico " + clienteDireccion.getCorreoElectronico() + " ya existe en la BD");
		}
		
		ClienteEntity clienteEntity = new ClienteEntity();
		
		clienteEntity.setApellidoMaterno(clienteO.get().getApellidoMaterno());
		clienteEntity.setApellidoPaterno(clienteO.get().getApellidoPaterno());
		clienteEntity.setCorreoElectronico(clienteO.get().getCorreoElectronico());
		clienteEntity.setEstatus(ESTATUS_ACTIVO);
		clienteEntity.setNombre(clienteO.get().getNombre());
		clienteEntity.setPassword(passwordEncoder.encode(clienteO.get().getPassword()));
		clienteEntity.setTelefono(clienteO.get().getTelefono());
		clienteEntity.setCodigoVerificacion(clienteO.get().getCodigoVerificacion());
		clienteEntity.setTipo("general");


		// Enviar correo de confirmación
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setTo(clienteDireccion.getCorreoElectronico());
		helper.setSubject("Confirmación de registro");
		String htmlContent = "<html><body>" + "<p>Hola " + clienteEntity.getNombre() + ",</p>"
				+ "<p>Para poder iniciar sesion correctamente ingresa el siguiente codigo:</p>"
				+ "<p>"+ clienteEntity.getCodigoVerificacion()+"</p>" + "</body></html>";
		helper.setText(htmlContent, true);
		javaMailSender.send(message);

		SingleResponse<ClienteEntity> response = new SingleResponse<>();
		response.setOk(true);
		response.setMensaje("Se ha guardado al cliente " + clienteEntity.getNombre() + " exitosamente.");
		response.setResponse(clienteEntity);
		return response;
	}

	@Override
	public SingleResponse<ClienteEntity> registerUser(ClienteEntity clienteDireccion) throws MessagingException {
		// Código para registrar al usuario

		String randomCode = RandomString.make(64);

		Optional<ClienteEntity> clienteO = Optional.empty();
		try {
			clienteO = clienteRepository.findBycorreoElectronicoIgnoreCase(clienteDireccion.getCorreoElectronico());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los usuarios en la BD");
		}

		if (clienteO.isPresent()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST,
					"El  correo electrónico " + clienteDireccion.getCorreoElectronico() + " ya existe en la BD");
		}

		ClienteEntity clienteEntity = new ClienteEntity();

//		if (clienteDireccion.getMultipartFile().isEmpty() || clienteDireccion.getMultipartFile() != null) {
//			clienteEntity.setNombreImagen(clienteDireccion.getMultipartFile().getOriginalFilename());
//			clienteEntity.setTipoImagen(clienteDireccion.getMultipartFile().getContentType());
//			try {
//				clienteEntity.setBytesImagen(clienteDireccion.getMultipartFile().getBytes());
//			} catch (IOException e) {
//				throw new BusinessException(HttpStatus.BAD_REQUEST, "Error al procesar la imagen en el sistema.");
//			}
//		}

		clienteEntity.setApellidoMaterno(clienteDireccion.getApellidoMaterno());
		clienteEntity.setApellidoPaterno(clienteDireccion.getApellidoPaterno());
		clienteEntity.setCorreoElectronico(clienteDireccion.getCorreoElectronico());
		clienteEntity.setEstatus(ESTATUS_INACTIVO);
		clienteEntity.setNombre(clienteDireccion.getNombre());
		clienteEntity.setPassword(passwordEncoder.encode(clienteDireccion.getPassword()));
		clienteEntity.setTelefono(clienteDireccion.getTelefono());
		clienteEntity.setCodigoVerificacion(randomCode);
		clienteEntity.setTipo("general");
	

		try {
			clienteEntity = clienteRepository.save(clienteEntity);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los clientes2 en la BD");
		}

		// Enviar correo de confirmación
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setTo(clienteDireccion.getCorreoElectronico());
		helper.setSubject("Confirmación de registro");
		String htmlContent = "<html><body>" + "<p>Hola " + clienteDireccion.getNombre() + ",</p>"
				+ "<p>Gracias por registrarte en nuestra aplicación. Para confirmar tu registro, haz clic en el botón a continuación:</p>"
				+ "<a href='http://localhost:8081/registro/confirmar/" + clienteEntity.getCodigoVerificacion()
				+ "'><button style='background-color: #008CBA; color: white; padding: 10px;'>Confirmar registro</button></a>"
				+ "<p>Saludos,<br>El equipo de nuestra aplicación</p>" + "</body></html>";
		helper.setText(htmlContent, true);
		javaMailSender.send(message);

		SingleResponse<ClienteEntity> response = new SingleResponse<>();
		response.setOk(true);
		response.setMensaje("Se ha guardado al cliente " + clienteEntity.getNombre() + " exitosamente.");
		response.setResponse(clienteEntity);
		clienteDireccion.setMultipartFile(null);
		return response;

	}

	public void confirmRegistration(String codigo) {
		Optional<ClienteEntity> usuario = Optional.empty();

		try {
			usuario = clienteRepository.findByClienteCodigo(codigo);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los usuarios en la BD");
		}

		if (usuario.isPresent()) {
			usuario.get().setEstatus(ESTATUS_ACTIVO);
		}

		try {
			clienteRepository.save(usuario.get());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los usuarios en la BD");
		}
	}

	public SingleResponse<ClienteEntity> confirmPassword(ClienteEntity cliente, String codigo) {
		Optional<ClienteEntity> clienetO = Optional.empty();

		try {
			clienetO = clienteRepository.findBycorreoElectronicoIgnoreCase(cliente.getCorreoElectronico());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los usuarios en la BD");
		}

		log.info(clienetO.get().getPassword());

		ClienteEntity clienteDb = clienetO.get();

		String encodedPassword = clienteDb.getPassword();

		if (passwordEncoder.matches(cliente.getPassword(), encodedPassword)) {
			clienteDb.setPassword(passwordEncoder.encode(codigo));
		} else {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Contraseña incorrecta");
		}

		try {
			clienteRepository.save(clienteDb);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error alcambiar la contraseña en la BD");
		}		

		SingleResponse<ClienteEntity> response = new SingleResponse<>();
		response.setOk(true);
		response.setMensaje("Se ha guardado al cliente " + clienteDb.getNombre() + " exitosamente.");
		return response;
	}

}
