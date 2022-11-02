package com.springboot.crud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.crud.entity.Cliente;
import com.springboot.crud.services.IClienteService;

@CrossOrigin(origins = {"http://localhost:4200"}) // 1.
@RestController
@RequestMapping("/api")
public class ClienteRestController {

    @Autowired
    public IClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> index() { 
        return clienteService.findAll();
    }

    @GetMapping("/clientes/{id}")
    public Cliente show(@PathVariable Long id) { // 2.
        return clienteService.findById(id);
    }

    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED) // 3. 
    public Cliente create(@RequestBody Cliente cliente) { // 4.
        return clienteService.save(cliente);
    }

    @PutMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.CREATED) // 3.
    public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) { // 4. // 2.
        Cliente clienteActual = clienteService.findById(id);
        clienteActual.setNombre(cliente.getNombre());
        clienteActual.setApellido(cliente.getApellido());
        clienteActual.setEmail(cliente.getEmail());
        return clienteService.save(clienteActual);
    }

    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 5. 
    public void delete(@PathVariable Long id) {
        clienteService.delete(id);
    }
}

// 1. @CrossOrigin - Cruce de datos: concede el acceso a la url los métodos @Get @Post @Put @Delete ...
// 2. @PathVariable - Hace referencia al parámetro en la url => {id}
// 3. @ResponseStatus(HttpStatus.CREATED) - Retorna un status 201 created
// 4. @RequestBody - Ayuda con el json que trae el cliente {No queda muy claro}
// 5. @ResponseStatus(HttpStatus.NO_CONTENT) - Retorna un status 204 no content