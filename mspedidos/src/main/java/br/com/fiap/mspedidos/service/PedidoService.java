package br.com.fiap.mspedidos.service;

import br.com.fiap.mspedidos.dto.EnderecoEntregaDTO;
import br.com.fiap.mspedidos.dto.PedidoDTO;
import br.com.fiap.mspedidos.dto.StatusPedidoDTO;
import br.com.fiap.mspedidos.integracao.msprodutos.service.IntegracaoProdutoService;
import br.com.fiap.mspedidos.model.*;
import br.com.fiap.mspedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        Pedido pedido = criarPedido(pedidoDTO, itensPedido);
        pedido.setItensPedido(itensPedido);
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> buscarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido getById(String id) {
        return pedidoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Pedido não encontrado")
        );
    }

    public Pedido atualizarStatus(String entregaID, StatusPedidoDTO status) {
        var pedido = getById(entregaID);
        pedido.setStatus(status.status());
        return pedidoRepository.save(pedido);
    }

    private Pedido criarPedido(PedidoDTO pedidoDTO, List<ItemPedido> itensPedido) {
        Pedido pedido = new Pedido();
        pedido.setClientId(pedidoDTO.getClienteId());
        pedido.setDataPedido(Calendar.getInstance());
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedido.setItensPedido(itensPedido);
        preencherDadosEndereco(pedido, pedidoDTO);
        preencherDadosPagamento(pedido, pedidoDTO);

        return pedido;
    }

    private void preencherDadosEndereco(Pedido pedido, PedidoDTO pedidoDTO) {
        Endereco endereco = new Endereco();
        EnderecoEntregaDTO enderecoEntregaDTO = pedidoDTO.getEnderecoEntrega();
        endereco.setLogradouro(enderecoEntregaDTO.getLogradouro());
        endereco.setNumero(enderecoEntregaDTO.getNumero());
        endereco.setComplemento(enderecoEntregaDTO.getComplemento());
        endereco.setBairro(enderecoEntregaDTO.getBairro());
        endereco.setCidade(enderecoEntregaDTO.getCidade());
        endereco.setUf(enderecoEntregaDTO.getUf());
        endereco.setCep(enderecoEntregaDTO.getCep());
        pedido.setEnderecoEntrega(endereco);
    }

    private void preencherDadosPagamento(Pedido pedido, PedidoDTO pedidoDTO){
        BigDecimal valorPedido = calcularValorTotalPedido(pedido.getItensPedido());
        Pagamento pagamento = new Pagamento();
        pagamento.setValorTotal(valorPedido);
        pagamento.setFormaPagamento(pedidoDTO.getFormaPagamento());
        pedido.setPagamento(pagamento);
    }

    private BigDecimal calcularValorTotalPedido(List<ItemPedido> itensPedido) {
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (ItemPedido itemPedido : itensPedido) {
            BigDecimal valorItem = itemPedido.getValorUnitario().multiply(BigDecimal.valueOf(itemPedido.getQuantidade()));
            valorTotal = valorTotal.add(valorItem);
        }
        return valorTotal;
    }
}
