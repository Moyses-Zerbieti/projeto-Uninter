package com.moyses.projeto_backend_uninter.controller;

import com.moyses.projeto_backend_uninter.dto.ProdutoRequest;
import com.moyses.projeto_backend_uninter.model.Produto;
import com.moyses.projeto_backend_uninter.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produto")
@Tag(name = "Produto")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping("/criar")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(summary = "Cria produto no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "400", description = "Problema na requisição"),
            @ApiResponse(responseCode = "405", description = "Erro na URL")
    })
    public ResponseEntity<?>criar(@RequestBody ProdutoRequest request){
        service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole ('ADMIN', 'GERENTE', 'ATENDENTE', 'CLIENTE')")
    @Operation(summary = "Informa todos os produtos disponíveis")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Não existe produtos cadastrados"),
            @ApiResponse(responseCode = "400", description = "Requisição não encontrada"),
    })
    public ResponseEntity<List<Produto>> listarProdutos(){
        return ResponseEntity.ok(service.listarProdutos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole ('ADMIN', 'GERENTE', 'ATENDENTE')")
    @Operation(summary = "Busca um produto pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "400", description = "Requisição não encontrada"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
    })
    public ResponseEntity<Produto> obterPorId(@PathVariable UUID id){
        return ResponseEntity.ok(service.obterProdutoPorId(id));
    }

}
