package com.example.tiendaVeterinaria.service;

import javax.mail.MessagingException;

import com.example.tiendaVeterinaria.model.dto.SingleResponse;
import com.example.tiendaVeterinaria.model.entity.ClienteEntity;

public interface IRegistroService {

	SingleResponse<ClienteEntity> registerUser(ClienteEntity clienteDireccion) throws MessagingException ;
	
	SingleResponse<ClienteEntity> resetPassword(ClienteEntity clienteDireccion) throws MessagingException ;
	
	SingleResponse<ClienteEntity> confirmarCodigo(ClienteEntity clienteDireccion) throws MessagingException ;
}
