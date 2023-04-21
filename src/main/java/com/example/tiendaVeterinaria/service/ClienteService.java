package com.example.tiendaVeterinaria.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiendaVeterinaria.exceptions.BusinessException;
import com.example.tiendaVeterinaria.model.dto.ClienteDireccion;
import com.example.tiendaVeterinaria.model.dto.ClienteResponse;
import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ClienteEntity;
import com.example.tiendaVeterinaria.repository.IClienteRepository;
import com.example.tiendaVeterinaria.repository.IDireccionRepository;


@Service
public class ClienteService implements IClienteService {

	public static final Integer ESTATUS_ACTIVO = 1;
	public static final Integer ESTATUS_INACTIVO = 0;
	private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

	@Autowired
	private IClienteRepository clienteRepository;

	@Autowired
	private IDireccionRepository direccionRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public SingleResponse<List<ClienteEntity>> consultarClientes() {
		List<ClienteEntity> listaCliente = new ArrayList<>();

		try {
			listaCliente = clienteRepository.findAll();
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
		}
		
		if (!listaCliente.isEmpty()) {
			SingleResponse<List<ClienteEntity>> response = new SingleResponse<>();
			response.setOk(true);
			response.setMensaje("Se ha obtenido la lista de clientes exitosamente");
			response.setResponse(listaCliente);
			return response;
		}

		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registros de clientes en la BD");
	}

	@Transactional
	@Override
	public SingleResponse<ClienteEntity> crearCliente(ClienteEntity clienteDireccion) {
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

		if (clienteDireccion.getMultipartFile().isEmpty() || clienteDireccion.getMultipartFile() != null) {
			clienteEntity.setNombreImagen(clienteDireccion.getMultipartFile().getOriginalFilename());
			clienteEntity.setTipoImagen(clienteDireccion.getMultipartFile().getContentType());
			try {
				clienteEntity.setBytesImagen(clienteDireccion.getMultipartFile().getBytes());
			} catch (IOException e) {
				throw new BusinessException(HttpStatus.BAD_REQUEST, "Error al procesar la imagen en el sistema.");
			}
		}

		clienteEntity.setApellidoMaterno(clienteDireccion.getApellidoMaterno());
		clienteEntity.setApellidoPaterno(clienteDireccion.getApellidoPaterno());
		clienteEntity.setCorreoElectronico(clienteDireccion.getCorreoElectronico());
		clienteEntity.setEstatus(ESTATUS_INACTIVO);
		clienteEntity.setNombre(clienteDireccion.getNombre());
		clienteEntity.setPassword(passwordEncoder.encode(clienteDireccion.getPassword()));
		clienteEntity.setTelefono(clienteDireccion.getTelefono());
		
		

		try {
			clienteEntity = clienteRepository.save(clienteEntity);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los clientes2 en la BD");
		}

//		try {
//			direccionservice.crearDireccion(clienteDireccion, clienteEntity);
//		} catch (DataAccessException ex) {
//			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
//					ex.getStackTrace());
//			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los clientes2 en la BD");
//		}

		SingleResponse<ClienteEntity> response = new SingleResponse<>();
		response.setOk(true);
		response.setMensaje("Se ha guardado al cliente " + clienteEntity.getNombre() + " exitosamente.");
		response.setResponse(clienteEntity);
		clienteDireccion.setMultipartFile(null);
		return response;
	}

	@Transactional
	@Override
	public SingleResponse<ClienteEntity> eliminarCliente(Integer id) {
		SingleResponse<ClienteEntity> response = new SingleResponse<>();
		Optional<ClienteEntity> clienteO = Optional.empty();

		try {
			clienteO = clienteRepository.findById(id);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
		}

		if (!clienteO.isPresent()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST, "El  usuario con Id " + id + " no existe en la BD");
		}

		try {
			direccionRepository.deleteCliente(id);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
		}

		try {
			clienteRepository.delete(clienteO.get());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
		}

		response.setOk(true);
		response.setMensaje("Cliente eliminado exitosamente");
		response.setResponse(clienteO.get());
		return response;

	}

	@Transactional
	@Override
	public SingleResponse<ClienteEntity> actualizarCliente(ClienteEntity cliente) {
		Optional<ClienteEntity> clienteOBd = Optional.empty();

		try {
			clienteOBd = clienteRepository.findById(cliente.getIdCliente());
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar el cliente en la BD");
		}

		if (!clienteOBd.isPresent()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST,
					"El  cliente con Id " + cliente.getNombre() + " no existe en la BD");
		}

		ClienteEntity clienteDB = clienteOBd.get();

		validarParmetros(cliente, clienteDB);

		try {
			clienteDB = clienteRepository.save(clienteDB);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los usuarios en la BD");
		}

		SingleResponse<ClienteEntity> response = new SingleResponse<>();

		response.setOk(true);
		response.setMensaje("Se ha actualizado al cliente " + clienteDB.getNombre() + " exitosamente.");
		response.setResponse(clienteDB);
		return response;
	}

	@Transactional
	@Override
	public SingleResponse<ClienteEntity> consultarClientePorId(Integer clienteid) {
		Optional<ClienteEntity> oClienteDb = Optional.empty();

		try {
			oClienteDb = clienteRepository.findById(clienteid);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar el cleinet en la BD");
		}

		ClienteEntity clienteEn = oClienteDb.get();

		if (!oClienteDb.isEmpty()) {
			SingleResponse<ClienteEntity> response = new SingleResponse<>();
			response.setOk(true);
			response.setMensaje("Se ha obtenido al Cliente exitosamente");
			response.setResponse(clienteEn);
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registros de cliente en la BD");

	}

	@Transactional
	@Override
	public SingleResponse<List<ClienteEntity>> consultarClientesActivos() {
		List<ClienteEntity> listaClientes = new ArrayList<>();

		try {
			listaClientes = clienteRepository
					.findByEstatusOrderByApellidoPaternoAscApellidoMaternoAscNombreAsc(ESTATUS_ACTIVO);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los usuarios en la BD");
		}
		if (!listaClientes.isEmpty()) {
			SingleResponse<List<ClienteEntity>> response = new SingleResponse<>();
			response.setOk(true);
			response.setMensaje("Se ha obtenido la lista de usuarios activos exitosamente");
			response.setResponse(listaClientes);
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registros de clientes activos en la BD");
	}

	/*
	 * Metodo para vaidar parametros
	 */
	private void validarParmetros(ClienteEntity cliente, ClienteEntity clienteDB) {

		if (cliente.getNombre() != null) {
			clienteDB.setNombre(cliente.getNombre());
		}

		if (cliente.getApellidoPaterno() != null) {
			clienteDB.setApellidoPaterno(cliente.getApellidoPaterno());
		}

		if (cliente.getApellidoMaterno() != null) {
			clienteDB.setApellidoMaterno(cliente.getApellidoMaterno());
		}

		if (cliente.getCorreoElectronico() != null) {
			Optional<ClienteEntity> clienteOo = Optional.empty();
			try {
				clienteOo = clienteRepository.findBycorreoElectronicoIgnoreCase(cliente.getCorreoElectronico());
			} catch (DataAccessException ex) {
				log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
						ex.getStackTrace());
				throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Error al consultar los usuarios en la BD");
			}
			if (clienteOo.isPresent()) {
				throw new BusinessException(HttpStatus.BAD_REQUEST,
						"El  correo electrónico " + cliente.getCorreoElectronico() + " ya existe en la BD");
			}
			clienteDB.setCorreoElectronico(cliente.getCorreoElectronico());
		}
		if (cliente.getEstatus() != null) {
			clienteDB.setEstatus(cliente.getEstatus());
		}

		if (cliente.getPassword() != null) {
			clienteDB.setPassword(passwordEncoder.encode(cliente.getPassword()));
		}

		if (!cliente.getMultipartFile().isEmpty() || cliente.getMultipartFile() != null) {
			clienteDB.setNombreImagen(cliente.getMultipartFile().getOriginalFilename());
			clienteDB.setTipoImagen(cliente.getMultipartFile().getContentType());
			try {
				clienteDB.setBytesImagen(cliente.getMultipartFile().getBytes());
			} catch (IOException e) {
				throw new BusinessException(HttpStatus.BAD_REQUEST, "Error al procesar la imagen en el sistema.");
			}
		}

	}

	@Transactional
	@Override
	public SingleResponse<Page<ClienteEntity>> consultarPorPaginas(int noPagina, String campo, String direccion,
			String buscar) {
		int pageSize = 10;
		Pageable pageable = PageRequest.of(noPagina - 1, pageSize,
				direccion.equalsIgnoreCase("asc") ? Sort.by(campo).ascending() : Sort.by(campo).descending());

		Page<ClienteEntity> clientePage = Page.empty();

		try {
			if (buscar != null) {
				clientePage = clienteRepository.findAll(buscar, pageable);
			} else {
				clientePage = clienteRepository.findAll(pageable);
			}

		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los clientes en la BD");
		}

		if (!clientePage.isEmpty()) {
			SingleResponse<Page<ClienteEntity>> response = new SingleResponse<>();
			response.setOk(true);
			response.setMensaje("Se ha obtenido la lista de usuarios activos exitosamente");
			response.setResponse(clientePage);
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "No se encontraron registros en la página " + noPagina);

	}

	@Override
	public SingleResponse<ClienteEntity> loginCliente(ClienteEntity cliente) {
		Optional<ClienteEntity> oClienteDb = Optional.empty();
		try {
			oClienteDb = clienteRepository.findByCorreoElectronicoIgnoreCaseAndEstatus(cliente.getCorreoElectronico(),
					ESTATUS_ACTIVO);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
			throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar los clientes en la BD");
		}
		if (!oClienteDb.isPresent()) {
			throw new BusinessException(HttpStatus.BAD_REQUEST,
					"No se encontró registro de usuario con el correo electrónico: " + cliente.getCorreoElectronico());
		}
		ClienteEntity usuarioDb = oClienteDb.get();
		
//		String token = getJWTToken(usuarioDb.getCorreoElectronico());
//		
//		usuarioDb.setToken(token);
		
		String encodedPassword = usuarioDb.getPassword();
		if (passwordEncoder.matches(cliente.getPassword(), encodedPassword)) {
			SingleResponse<ClienteEntity> response = new SingleResponse<>();
			response.setOk(true);
			response.setMensaje("Login exitoso.");
			response.setResponse(usuarioDb);
			return response;
		}
		throw new BusinessException(HttpStatus.BAD_REQUEST, "Contraseña incorrecta");
	}
	
	
//	private String getJWTToken(String username) {
//		String secretKey = "mySecretKey";
//		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
//				.commaSeparatedStringToAuthorityList("ROLE_USER");//Creamos la lista con los Roles de los usuarios  
//		
//		String token = Jwts
//				.builder()
//				.setId("softtekJWT")//establecemos unidentificador para cada token
//				.setSubject(username)//Se identifica el sujeto del Token es decir, la entidad o el usuario al que se refiere el token.
//				.claim("authorities",
//						grantedAuthorities.stream()
//								.map(GrantedAuthority::getAuthority)
//								.collect(Collectors.toList()))
//				.setIssuedAt(new Date(System.currentTimeMillis()))// establece la fecha y hora en que se emitió el token como la fecha y hora actual,
//				.setExpiration(new Date(System.currentTimeMillis() + 600000))//Establece el tiempo de expiracion del token (10 min)
//				.signWith(SignatureAlgorithm.HS512, //especifica el algoritmo de firma utilizado para firmar el token y la clave secreta utilizada para la firma
//						secretKey.getBytes()).compact();//se utiliza para "compactar" los datos del token JWT y devolverlos como una cadena de texto. Esto significa que se eliminan los espacios en blanco y otros caracteres innecesarios;
//
//		return "Bearer " + token;
//	}
	
	
	

}
