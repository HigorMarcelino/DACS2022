package br.univille.coredacs2022.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String user;
    private String senha;

    public long getId() {
        return id;
    }
    public String getUser() {
        return user;
    }
    public String getSenha() {
        return senha;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    
    
}
