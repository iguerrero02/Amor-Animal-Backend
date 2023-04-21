package com.example.tiendaVeterinaria.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.tiendaVeterinaria.model.entity.ClienteEntity;
import com.example.tiendaVeterinaria.repository.IClienteRepository;



@Service
public class ClienteDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	private IClienteRepository clienteRepository;
	
	private static final Logger log = LoggerFactory.getLogger(ClienteDetailServiceImpl.class);
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<ClienteEntity> cliente = Optional.empty();

		try {
			cliente= clienteRepository.findBycorreoElectronicoIgnoreCase(email);
		} catch (DataAccessException ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
		}
		ClienteEntity clien = cliente.get();		
		return new ClienteDetailsImpl(clien);
	}

}













