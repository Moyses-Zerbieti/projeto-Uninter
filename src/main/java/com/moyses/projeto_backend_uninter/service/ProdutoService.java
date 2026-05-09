package com.moyses.projeto_backend_uninter.service;

import com.moyses.projeto_backend_uninter.dto.ProdutoRequest;
import com.moyses.projeto_backend_uninter.model.Produto;
import com.moyses.projeto_backend_uninter.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto criar(ProdutoRequest request){
        Produto produto = new Produto(
                request.nome,
                request.preco,
                request.estoque
        );

        return repository.save(produto);
    }


    public List<Produto> listarProdutos(){
        return repository.findAll();
    }

    public Produto obterProdutoPorId(UUID id){
        return repository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));
    }
}
