package com.moyses.projeto_backend_uninter.controller;

import com.moyses.projeto_backend_uninter.dto.PedidoRequest;
import com.moyses.projeto_backend_uninter.model.Pedido;
import com.moyses.projeto_backend_uninter.model.Produto;
import com.moyses.projeto_backend_uninter.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pedido")
@Tag(name = "Pedido")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping("/pedir")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ATENDENTE', 'CLIENTE')")
    @Operation(summary = "Efetua o pedido de produto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido efetuado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<Pedido>fazerPedido(@RequestBody PedidoRequest request){

        Pedido pedido = service.fazerPedido(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pedido);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ATENDENTE')")
    @Operation(summary = "Efetua o pedido de produto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<Pedido> consultarPedido(@PathVariable UUID id){
        Pedido pedido = service.consultarPedido(id);

        return ResponseEntity.ok(pedido);
    }

    @PatchMapping("/{id}/preparar")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')")
    @Operation(summary = "Altera o status do pedido para preparar")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido em preparo"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "422", description = "É necessário efetuar o pagamento"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor"),
    })
    public ResponseEntity<Pedido> prepararPedido(@PathVariable UUID id){
        Pedido pedido = service.prepararPedido(id);

        return ResponseEntity.ok(pedido);
    }

    @PatchMapping("/{id}/aguardar-retirada")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')")
    @Operation(summary = "Altera o status do pedido para aguardar a retirada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Aguardando retirada do entregador"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "422", description = "O pedido ainda não está em preparo"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor"),
    })
    public ResponseEntity<Pedido> aguardaRetirada(@PathVariable UUID id){
        Pedido pedido = service.aguardandoEntregador(id);

        return ResponseEntity.ok(pedido);
    }

    @PatchMapping("/{id}/saiu-entrega")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')")
    @Operation(summary = "Altera o status do pedido: Já saiu para entrega")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido retirado pelo entregador"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "422", description = "Pedido está em preparo"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor"),
    })
    public ResponseEntity<Pedido> saiuParaEntrega(@PathVariable UUID id){
        Pedido pedido = service.saiuParaEntrega(id);

        return ResponseEntity.ok(pedido);
    }

    @PatchMapping("/{id}/entregue")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')")
    @Operation(summary = "Pedido entregue")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido entregue com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "422", description = "Pedido aguardando para ser retirado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor"),
    })
    public ResponseEntity<Pedido> pedidoEntregue(@PathVariable UUID id){
        Pedido pedido = service.pedidoEntregue(id);

        return ResponseEntity.ok(pedido);
    }


    @PatchMapping("/{id}/cancelar")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')")
    @Operation(summary = "Cancela o pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido cancelado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "422", description = "Pedido já foi entregue, entre em contato com a loja para devolução"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor"),
    })
    public ResponseEntity<Pedido> cancelarPedido(@PathVariable UUID id){
        Pedido pedido = service.cancelaPedido(id);

        return ResponseEntity.ok(pedido);
    }



}
