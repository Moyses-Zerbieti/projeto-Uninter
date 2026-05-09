package com.moyses.projeto_backend_uninter.repository;

import com.moyses.projeto_backend_uninter.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {
}
