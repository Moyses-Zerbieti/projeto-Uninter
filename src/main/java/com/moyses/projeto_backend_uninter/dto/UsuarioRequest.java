package com.moyses.projeto_backend_uninter.dto;

import com.moyses.projeto_backend_uninter.model.Usuario;

public class UsuarioRequest {

    public String nome;

    public String email;

    public String senha;

    public String role;

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getRole() {
        return role;
    }
}
