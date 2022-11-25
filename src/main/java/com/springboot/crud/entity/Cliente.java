package com.springboot.crud.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
// import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
        
    @NotEmpty(message = "no puede estar vacio")
    @Size(min = 4, max = 12, message="tiene que estar entre 4 y 12")    
    @Column(nullable = false)
    private String nombre;

    @NotEmpty(message = "no puede estar vacio")
    private String apellido;
    
    @NotEmpty(message = "no puede estar vacio")
    @Email(message = "tiene un formato no valido")
    @Column(nullable = false, unique = false)
    private String email;

    @NotNull
    @Column(name = "fecha") // 1.
    @Temporal(TemporalType.DATE) // 2.
    private Date createAt;

    // @PrePersist // 3.
    // public void prePersist() {
    //     createAt = new Date();
    // }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    // 1. Se puede colocar @Column en la variable donde se quiere un nombre distinto en la db
    // 2. Implicitamente se hace esto pero se recomienda colocarlo
    // 3. crea la fecha automáticamente
}
