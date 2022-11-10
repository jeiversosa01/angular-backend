package com.springboot.crud.dao;

import com.springboot.crud.entity.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteDao extends JpaRepository<Cliente, Long>{
    
}
