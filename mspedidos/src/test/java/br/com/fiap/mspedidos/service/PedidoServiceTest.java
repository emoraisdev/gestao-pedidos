package br.com.fiap.mspedidos.service;

import br.com.fiap.mspedidos.dto.EnderecoEntregaDTO;
import br.com.fiap.mspedidos.dto.ItemPedidoDTO;
import br.com.fiap.mspedidos.dto.PedidoDTO;
import br.com.fiap.mspedidos.dto.StatusPedidoDTO;
import br.com.fiap.mspedidos.integracao.msprodutos.service.IntegracaoProdutoService;
import br.com.fiap.mspedidos.model.FormaPagamento;
import br.com.fiap.mspedidos.model.ItemPedido;
import br.com.fiap.mspedidos.model.Pedido;
import br.com.fiap.mspedidos.model.StatusPedido;
import br.com.fiap.mspedidos.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private IntegracaoProdutoService integracaoProdutoService;

    @InjectMocks
    private PedidoService pedidoService;

    @Captor
    ArgumentCaptor<Pedido> pedidoCaptor;

    @Test
    void develListarTodosPedidos() {
        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(buildPedido("pedido-01")));

        List<Pedido> pedidos = pedidoService.buscarTodos();

        verify(pedidoRepository, times(1)).findAll();
        assertThat(pedidos).hasSize(1);
        assertThat(pedidos.get(0).getId()).isEqualTo("pedido-01");
    }

    @Test
    void deveRetornarPedidoPorId(){
        Optional<Pedido> optionalPedido = Optional.of(buildPedido("pedido-01"));
        when(pedidoRepository.findById("pedido-01")).thenReturn(optionalPedido);

        Pedido pedido = pedidoService.getById("pedido-01");

        verify(pedidoRepository, times(1)).findById("pedido-01");
        assertThat(pedido).isNotNull();
        assertThat(pedido.getId()).isEqualTo("pedido-01");
    }

    @Test
    void deveLancarRunTimeExceptionQuandoPedidoNaoEncontrado(){
        Optional<Pedido> optionalPedido = Optional.empty();
        when(pedidoRepository.findById("pedido-01")).thenReturn(optionalPedido);

        try {
            pedidoService.getById("pedido-01");
            fail("Não retornou um exception");
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Pedido não encontrado");
        }

        verify(pedidoRepository, times(1)).findById("pedido-01");
    }

    @Test
    void deveAtualizarStatusDoPedidoParaCancelado() {
        Optional<Pedido> optionalPedido = Optional.of(buildPedido("pedido-01"));
        when(pedidoRepository.findById("pedido-01")).thenReturn(optionalPedido);

        pedidoService.atualizarStatus("pedido-01", new StatusPedidoDTO(StatusPedido.CANCELADO));
        verify(pedidoRepository, times(1)).findById("pedido-01");
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
        assertThat(optionalPedido.get().getStatus()).isEqualTo(StatusPedido.CANCELADO);
    }

    @Test
    void deveCriarNovoPedido() {
        PedidoDTO pedidoDTO = criarPedidoDTO();
        List<ItemPedido> itemPedidos = montarListItemPedido(pedidoDTO.getItensPedido());
        when(integracaoProdutoService.verificaAtualizaEstoque(pedidoDTO.getItensPedido())).thenReturn(itemPedidos);

        pedidoService.salvar(pedidoDTO);

        verify(pedidoRepository, times(1)).save(pedidoCaptor.capture());
        Pedido pedidoCaptorValue = pedidoCaptor.getValue();
        Calendar dataAtual = Calendar.getInstance();
        assertThat(pedidoCaptorValue.getStatus()).isEqualTo(StatusPedido.AGUARDANDO_PAGAMENTO);
        assertThat(pedidoCaptorValue.getClientId()).isEqualTo(1L);
        assertThat(pedidoCaptorValue.getDataPedido().get(Calendar.DATE)).isEqualTo(dataAtual.get(Calendar.DATE));
        assertThat(pedidoCaptorValue.getDataPedido().get(Calendar.MONTH)).isEqualTo(dataAtual.get(Calendar.MONTH));
        assertThat(pedidoCaptorValue.getDataPedido().get(Calendar.YEAR)).isEqualTo(dataAtual.get(Calendar.YEAR));
        //endereco
        assertThat(pedidoCaptorValue.getEnderecoEntrega().getLogradouro()).isEqualTo("Rua Fernando Benevides");
        assertThat(pedidoCaptorValue.getEnderecoEntrega().getNumero()).isEqualTo("123");
        assertThat(pedidoCaptorValue.getEnderecoEntrega().getComplemento()).isNull();
        assertThat(pedidoCaptorValue.getEnderecoEntrega().getBairro()).isEqualTo("Cidade dos Funcionarios");
        assertThat(pedidoCaptorValue.getEnderecoEntrega().getCidade()).isEqualTo("Fortaleza");
        assertThat(pedidoCaptorValue.getEnderecoEntrega().getUf()).isEqualTo("CE");
        assertThat(pedidoCaptorValue.getEnderecoEntrega().getCep()).isEqualTo("60822115");
        //itensPedido
        assertThat(pedidoCaptorValue.getItensPedido().size()).isEqualTo(2);
        assertThat(pedidoCaptorValue.getItensPedido().get(0).getProdutoId()).isEqualTo(1l);
        assertThat(pedidoCaptorValue.getItensPedido().get(0).getQuantidade()).isEqualTo(10);
        assertThat(pedidoCaptorValue.getItensPedido().get(0).getValorUnitario()).isEqualTo(BigDecimal.valueOf(50));
        assertThat(pedidoCaptorValue.getItensPedido().get(1).getProdutoId()).isEqualTo(2l);
        assertThat(pedidoCaptorValue.getItensPedido().get(1).getQuantidade()).isEqualTo(10);
        assertThat(pedidoCaptorValue.getItensPedido().get(1).getValorUnitario()).isEqualTo(BigDecimal.valueOf(50));

        //pagamento
        assertThat(pedidoCaptorValue.getPagamento().getFormaPagamento()).isEqualTo(FormaPagamento.CARTAO_CREDITO);
        assertThat(pedidoCaptorValue.getPagamento().getValorTotal()).isEqualTo(BigDecimal.valueOf(1000));
    }

    private Pedido buildPedido(String pedidoId){
        Pedido pedido = new Pedido();
        pedido.setId(pedidoId);
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);

        return pedido;
    }

    private PedidoDTO criarPedidoDTO() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setClienteId(1L);
        pedidoDTO.setFormaPagamento(FormaPagamento.CARTAO_CREDITO);
        pedidoDTO.setEnderecoEntrega(
                new EnderecoEntregaDTO(
                "Rua Fernando Benevides",
                "123",
                null,
                "Cidade dos Funcionarios",
                "Fortaleza",
                "CE",
                "60822115"
                )
        );
        pedidoDTO.setItensPedido(
                Arrays.asList(
                        new ItemPedidoDTO(1l, 10),
                        new ItemPedidoDTO(2l, 10)
                )
        );

        return pedidoDTO;
    }

    private List<ItemPedido> montarListItemPedido(List<ItemPedidoDTO> itensPedidoDTO){
        List<ItemPedido> itemPedidos = new ArrayList<>();
        for (ItemPedidoDTO itemPedidoDTO : itensPedidoDTO) {
            itemPedidos.add(new ItemPedido(
                    null,
                    itemPedidoDTO.getProdutoId(),
                    itemPedidoDTO.getQuantidade(),
                    BigDecimal.valueOf(50L)
            ));
        }

        return itemPedidos;
    }

}
