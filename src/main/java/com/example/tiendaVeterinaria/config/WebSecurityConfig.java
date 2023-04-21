package com.example.tiendaVeterinaria.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig {
	

	private final ClienteDetailServiceImpl clienteDetailsService;
	private final UsuarioDetailsServiceImpl usuarioDetailsService;
	private final JWTAuthorizationFilter jwtAuthorizationFilter;
	
	@Bean
	SecurityFilterChain filterChainUser(HttpSecurity http, @Qualifier("clienteAuthManager") AuthenticationManager clienteAuthManager, @Qualifier("usuarioAuthManager") AuthenticationManager authManagerUsuario) throws Exception {
		
		JWTAuthenticationFilterUsuario userJwtAuthenticationFilter = new JWTAuthenticationFilterUsuario();
		userJwtAuthenticationFilter.setAuthenticationManager(authManagerUsuario);
		userJwtAuthenticationFilter.setFilterProcessesUrl("/loginUsuario");
		
		JWTAuthenticationFilter clientJwtAuthenticationFilter = new JWTAuthenticationFilter();
	    clientJwtAuthenticationFilter.setAuthenticationManager(clienteAuthManager);
	    clientJwtAuthenticationFilter.setFilterProcessesUrl("/loginCliente");
		
		return http
				.cors().and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/clientes/todos","/clientes/loginCliente","/registro/cliente",
						"/preguntaFrecuente/consultarTodosActivos","/Productos/todosEnStock",
						"/imagenbanner/todosActivos","usuarios/nuevoUsuario","/registro/confirmar/{idUsuario}").permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.httpBasic()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilter(clientJwtAuthenticationFilter)
				.addFilter(userJwtAuthenticationFilter)
				.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean(name = "clienteAuthManager")
	@Primary
	AuthenticationManager authManagerClient(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(clienteDetailsService)
				.passwordEncoder(passwordEncoder())
				.and()
				.build();
	}
	
	@Bean(name = "usuarioAuthManager")
	AuthenticationManager authManagerUsuario(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(usuarioDetailsService)
				.passwordEncoder(passwordEncoder())
				.and()
				.build();
	}
	
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS","PUT","DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization","enctype", "Authorization"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
	
		
}




















