package com.moyses.projeto_backend_uninter.repository;

import com.moyses.projeto_backend_uninter.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
}
