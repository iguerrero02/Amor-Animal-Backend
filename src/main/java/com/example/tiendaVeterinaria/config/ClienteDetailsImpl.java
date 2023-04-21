package com.example.tiendaVeterinaria.config;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.tiendaVeterinaria.model.entity.ClienteEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClienteDetailsImpl implements UserDetails {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3276659744262669306L;
	private final ClienteEntity cliente;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return cliente.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return cliente.getCorreoElectronico();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public String getNombre() {
		return cliente.getNombre();
	}

}
