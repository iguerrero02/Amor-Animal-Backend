package com.example.tiendaVeterinaria.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.tiendaVeterinaria.model.entity.UsuarioEntity;
import com.example.tiendaVeterinaria.repository.IUsuarioRepository;




@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioDetailsServiceImpl.class);
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UsuarioEntity> usuario = Optional.empty();
		
		try {
			usuario = usuarioRepository.findByCorreoElectronicoIgnoreCase(email);
		} catch (Exception ex) {
			log.error("Ha ocurrido un error inesperado. Exception {} {}", ex.getMessage() + " " + ex,
					ex.getStackTrace());
		}
		
		UsuarioEntity usu = usuario.get();
		return new UsuarioDetailsImpl(usu);
	}

}
