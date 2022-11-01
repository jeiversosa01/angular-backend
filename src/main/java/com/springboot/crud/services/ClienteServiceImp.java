package com.springboot.crud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.crud.dao.IClienteDao;
import com.springboot.crud.entity.Cliente;

@Service
public class ClienteServiceImp implements IClienteService{

    @Autowired
    private IClienteDao clienteDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {        
        return (List<Cliente>) clienteDao.findAll();
    }
    
}
