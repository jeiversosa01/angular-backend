package com.springboot.crud.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    private String message;

    @GetMapping("/clientes")
    @ResponseStatus(HttpStatus.OK) // 3. // 5.
    public List<Cliente> index() { 
        return clienteService.findAll();
    }

    @GetMapping("/clientes/page/{page}")
    @ResponseStatus(HttpStatus.OK) // 3. // 5.
    public Page<Cliente> index(@PathVariable Integer page) { 
        Pageable pageable = PageRequest.of(page, 4);
        return clienteService.findAll(pageable);
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) { // 2.
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();        
        try {
            cliente = clienteService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            message = e.getMessage();
            response.put("error", message.concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }                
        if (cliente == null) {
            response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" No existe en la base de datos!")));                        
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }        
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @PostMapping("/clientes")    
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) { // 6. // 4. // 7.
        Cliente clienteNew = null;
        Map<String, Object> response = new HashMap<>();        
        if (result.hasErrors()) {  // Validación // 7.
            List<String> errors = new ArrayList<>();
            for(FieldError err: result.getFieldErrors()){
                errors.add("El campo '"+ err.getField() + "' " + err.getDefaultMessage());
            }
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);            
        }            
        try {  // Errores
            clienteNew = clienteService.save(cliente);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            message = e.getMessage();
            response.put("error", message.concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente ha sido creado con éxito!");
        response.put("cliente", clienteNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")    
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) { // 6. // 4. // 7. // 2.
        Cliente clienteActual = clienteService.findById(id);
        Cliente clienteUpdate ;
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {   // Validación // 8.
            List<String> errors = result.getFieldErrors()
            .stream()
            .map(err -> "El campo '"+ err.getField() + "' " + err.getDefaultMessage())
            .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (clienteActual == null) {
            response.put("mensaje", "Error, no se pude editar, el cliente ID: ".concat(id.toString().concat(" No existe en la base de datos!")));                        
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {  // Errores
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setEmail(cliente.getEmail());
            clienteActual.setCreateAt(cliente.getCreateAt());
            clienteUpdate = clienteService.save(clienteActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el cliente en la base de datos");
            message = e.getMessage();
            response.put("error", message.concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }        
        response.put("mensaje", "El cliente ha sido actualizado con éxito!");
        response.put("cliente", clienteUpdate);   
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @DeleteMapping("/clientes/{id}") 
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            clienteService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el cliente en la base de datos");
            message = e.getMessage();
            response.put("error", message.concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
        response.put("mensaje", "El cliente ha sido eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}

// 1. @CrossOrigin - Cruce de datos: concede el acceso a la url los métodos @Get @Post @Put @Delete ...
// 2. @PathVariable - Hace referencia al parámetro en la url => {id}
// 3. @ResponseStatus(HttpStatus.CREATED) - Retorna un status 201 created
// 4. @RequestBody - Ayuda con el json que trae el cliente {No queda muy claro}
// 5. @ResponseStatus(HttpStatus.NO_CONTENT) - Retorna un status 204 no content
// 6. @Valid - Valida los datos desde el back y sean correctos para la entidad 
// 7. BindingResult - Guarda el error en la variable, ¡Debe ir antes de un @PathVariable!
// 8. Validación para JDK 11 o superior
// 9. Validacion para JDK 8 