package com.springboot.crud.dao;

import com.springboot.crud.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDao extends CrudRepository<Cliente, Long>{
    
}
