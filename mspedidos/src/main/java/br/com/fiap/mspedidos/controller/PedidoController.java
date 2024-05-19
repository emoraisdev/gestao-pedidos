package br.com.fiap.mspedidos.controller;

import br.com.fiap.mspedidos.dto.PedidoDTO;
import br.com.fiap.mspedidos.integracao.msprodutos.service.IntegracaoProdutoService;
import br.com.fiap.mspedidos.model.Pedido;
import br.com.fiap.mspedidos.service.PedidoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/pedidos/")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() throws JsonProcessingException {
        List<Pedido> pedidos = pedidoService.buscarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping()
    public ResponseEntity<Pedido> novo(@Valid @RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoService.salvar(pedidoDTO);
        return ResponseEntity.ok(pedido);
    }

}
