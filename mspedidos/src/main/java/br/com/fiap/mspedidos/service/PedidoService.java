package br.com.fiap.mspedidos.service;

import br.com.fiap.mspedidos.dto.ItemPedidoDTO;
import br.com.fiap.mspedidos.dto.PedidoDTO;
import br.com.fiap.mspedidos.dto.StatusPedidoDTO;
import br.com.fiap.mspedidos.integracao.msprodutos.dto.ProdutoEstoqueResponseDTO;
import br.com.fiap.mspedidos.integracao.msprodutos.service.IntegracaoProdutoService;
import br.com.fiap.mspedidos.model.ItemPedido;
import br.com.fiap.mspedidos.model.Pedido;
import br.com.fiap.mspedidos.model.StatusPedido;
import br.com.fiap.mspedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final IntegracaoProdutoService integracaoProdutoService;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, IntegracaoProdutoService integracaoProdutoService) {
        this.pedidoRepository = pedidoRepository;
        this.integracaoProdutoService = integracaoProdutoService;
    }

    public Pedido salvar(PedidoDTO pedidoDTO) {
        List<ItemPedido> itensPedido = integracaoProdutoService.verificaAtualizaEstoque(pedidoDTO.getItensPedido());
        if (itensPedido.isEmpty()){
            throw new RuntimeException("Sem itens");
        }
        Pedido pedido = criarPedido(pedidoDTO);
        pedido.setItensPedido(itensPedido);
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> buscarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido getById(Long id) {
        return pedidoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Pedido não encontrado")
        );
    }

    public Pedido atualizarStatus(Long entregaID, StatusPedidoDTO status) {

        var pedido = getById(entregaID);
        pedido.setStatus(status.status());
        return pedidoRepository.save(pedido);
    }

    private Pedido criarPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setClientId(pedidoDTO.getClienteId());
        pedido.setDataPedido(Calendar.getInstance());
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedido.setItensPedido(new ArrayList<>());

        return pedido;
    }
}
