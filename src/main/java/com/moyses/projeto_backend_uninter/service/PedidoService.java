package com.moyses.projeto_backend_uninter.service;

import com.moyses.projeto_backend_uninter.dto.PedidoRequest;
import com.moyses.projeto_backend_uninter.model.*;
import com.moyses.projeto_backend_uninter.repository.PedidoRepository;
import com.moyses.projeto_backend_uninter.repository.ProdutoRepository;
import com.moyses.projeto_backend_uninter.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
    }

    public Pedido fazerPedido(PedidoRequest request){
        Usuario cliente = usuarioRepository.findById(request.clienteId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setCanalPedido(CanalPedido.valueOf(request.canalPedido));
        pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);

        List<ItemPedido> itens = new ArrayList<>();
        double valorTotal = 0;

        for (var itemRequest : request.itens){
            Produto produto = produtoRepository.findById(itemRequest.produtoId)
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

            if(produto.getEstoque() < itemRequest.quantidade){
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Estoque insuficiente");
            }

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemRequest.quantidade);
            item.setPreco(produto.getPreco());

            valorTotal += produto.getPreco() * itemRequest.quantidade;

            itens.add(item);
        }

        pedido.setItens(itens);
        pedido.setValorPedido(valorTotal);

        return pedidoRepository.save(pedido);
    }

    public Pedido consultarPedido(UUID id){
        return pedidoRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Pedido não encontrado"));
    }

    public Pedido prepararPedido(UUID id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Pedido não encontrado"));

        if (pedido.getStatusPedido() != StatusPedido.PAGO){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "É necessário efetuar o pagamento para prosseguir");
        }

        pedido.setStatusPedido(StatusPedido.EM_PREPARO);

        return pedidoRepository.save(pedido);
    }

    public Pedido aguardandoEntregador(UUID id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Pedido não encontrado"));


        if (pedido.getStatusPedido() != StatusPedido.EM_PREPARO){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "O pedido ainda não está em preparo");
        }

        pedido.setStatusPedido(StatusPedido.AGUARDANDO_ENTREGADOR);

        return pedidoRepository.save(pedido);
    }

    public Pedido saiuParaEntrega(UUID id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Pedido não encontrado"));


        if (pedido.getStatusPedido() != StatusPedido.AGUARDANDO_ENTREGADOR){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Pedido está em preparo");
        }

        pedido.setStatusPedido(StatusPedido.SAIU_PARA_ENTREGA);

        return pedidoRepository.save(pedido);
    }

    public Pedido pedidoEntregue(UUID id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Pedido não encontrado"));

        if (pedido.getStatusPedido() != StatusPedido.SAIU_PARA_ENTREGA){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Pedido aguardando para ser retirado");
        }

        pedido.setStatusPedido(StatusPedido.ENTREGUE);

        return pedidoRepository.save(pedido);
    }

    public Pedido cancelaPedido(UUID id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        if (pedido.getStatusPedido() == StatusPedido.ENTREGUE || pedido.getStatusPedido() == StatusPedido.CANCELADO){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Pedido já foi entregue, entre em contato com a loja para devolução");
        }

        pedido.setStatusPedido(StatusPedido.CANCELADO);

        return pedidoRepository.save(pedido);
    }



}
