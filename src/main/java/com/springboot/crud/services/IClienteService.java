package com.springboot.crud.services;

import java.util.List;

import com.springboot.crud.entity.Cliente;

public interface IClienteService {
    
    public List<Cliente> findAll();
}
