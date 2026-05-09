package com.moyses.projeto_backend_uninter.repository;

import com.moyses.projeto_backend_uninter.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository <Usuario, UUID> {

    Optional<Usuario> findById(UUID id);

    boolean existsByNome(String nome);


    Optional <Usuario> findByEmail(String email);
}
