package com.moyses.projeto_backend_uninter.repository;

import com.moyses.projeto_backend_uninter.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

}
