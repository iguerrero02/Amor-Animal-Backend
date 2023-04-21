package com.example.tiendaVeterinaria.model.dto;

import com.example.tiendaVeterinaria.model.entity.ClienteEntity;

public class ClienteResponse {
    private Integer clienteId;
    private ClienteEntity cliente;

    public ClienteResponse(Integer clienteId, ClienteEntity cliente) {
        this.clienteId = clienteId;
        this.cliente = cliente;
    }

    // Getters y setters para los campos

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }
}
