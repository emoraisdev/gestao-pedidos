package br.com.fiap.mspedidos.integracao.msprodutos.service;

import br.com.fiap.mspedidos.dto.ItemPedidoDTO;
import br.com.fiap.mspedidos.exceptions.RegraNegocioException;
import br.com.fiap.mspedidos.integracao.msprodutos.dto.ProdutoEstoqueResponseDTO;
import br.com.fiap.mspedidos.integracao.msprodutos.dto.ProdutoPedidoDTO;
import br.com.fiap.mspedidos.model.ItemPedido;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class IntegracaoProdutoService {

    @Value("${api.msproduto.server}")
    private String API_MSPRODUTOS;

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

    @Autowired
    public IntegracaoProdutoService(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    public List<ItemPedido> verificaAtualizaEstoque(List<ItemPedidoDTO> itensPedidoDTO) {
        List<ProdutoEstoqueResponseDTO> produtosEstoque = buscarProdutoEstoque(itensPedidoDTO);
        List<String> produtosIndisponiveis = verificarEstoque(produtosEstoque, itensPedidoDTO);

        if(!produtosIndisponiveis.isEmpty()){
            throw new RegraNegocioException(produtosIndisponiveis);
        }
        atualizarEstoque(itensPedidoDTO);
        return montarItensPedido(itensPedidoDTO, produtosEstoque);
    }

    private List<ItemPedido> montarItensPedido(List<ItemPedidoDTO> itensPedidoDTO, List<ProdutoEstoqueResponseDTO> produtosEstoque) {
        List<ItemPedido> itensPedido = new ArrayList<>();
        for(ItemPedidoDTO itemPedidoDTO : itensPedidoDTO){
            ProdutoEstoqueResponseDTO produtoEstoque = getProdutoEstoque(itemPedidoDTO.getProdutoId(), produtosEstoque);
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProdutoId(itemPedidoDTO.getProdutoId());
            itemPedido.setQuantidade(itemPedidoDTO.getQuantidade());
            itemPedido.setValorUnitario(BigDecimal.valueOf(produtoEstoque.preco()));
            itensPedido.add(itemPedido);
        }

        return itensPedido;
    }

    private List<String> verificarEstoque(List<ProdutoEstoqueResponseDTO> produtosEstoque, List<ItemPedidoDTO> produtosSolicitados) {
        List<String> criticas = new ArrayList<>();
        for (ItemPedidoDTO produtoSolicitado : produtosSolicitados) {
            ProdutoEstoqueResponseDTO produtoEstoque = getProdutoEstoque(produtoSolicitado.getProdutoId(), produtosEstoque);
            if(produtoEstoque.quantidadeEstoque() == 0){
                criticas.add(produtoEstoque.nome() + ": esgotado.");
            } else if (produtoEstoque.quantidadeEstoque() < produtoSolicitado.getQuantidade()) {
                criticas.add(produtoEstoque.nome() + ": quantidade solicitada não disponivel.");
            }
        }

        return criticas;
    }

    private void atualizarEstoque(List<ItemPedidoDTO> itemPedidoDTO) {
        List<ProdutoPedidoDTO> produtosPedido = itemPedidoDTO.stream().map(
                itemPedido -> new ProdutoPedidoDTO(itemPedido.getProdutoId(), itemPedido.getQuantidade())).toList();

        restTemplate.put (
            API_MSPRODUTOS + "/produtos/atualizar-estoque",
            produtosPedido
        );
    }

    private ProdutoEstoqueResponseDTO getProdutoEstoque(Long produtoId, List<ProdutoEstoqueResponseDTO> produtosEstoque) {
        ProdutoEstoqueResponseDTO produtoEstoque = null;
        for(ProdutoEstoqueResponseDTO produto : produtosEstoque) {
            if(produto.id().equals(produtoId)){
                produtoEstoque = produto;
                break;
            }
        }

        return produtoEstoque;
    }

    private String buildIds(List<ItemPedidoDTO> itensPedidoDTO){
        String ids = "";
        for(ItemPedidoDTO itemPedido: itensPedidoDTO){
            ids = ids.concat(",").concat(String.valueOf(itemPedido.getProdutoId()));
        }

        return  ids.substring(1);
    }

    private List<ProdutoEstoqueResponseDTO> buscarProdutoEstoque(List<ItemPedidoDTO> itensPedidoDTO) {
        String ids = buildIds(itensPedidoDTO);
        List<ProdutoEstoqueResponseDTO> produtosEstoque = new ArrayList<>();
        ResponseEntity<String> response = restTemplate.getForEntity(
                API_MSPRODUTOS + "/produtos?ids="+ids,
                String.class
        );

        if(response.getStatusCode() == HttpStatus.BAD_REQUEST){
            //TODO throw new RegraNegocioException(reponse.getMessage());
        } else if(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
            throw new RuntimeException("Erro ao atualizar estoque: " + response.getBody());
        }

        if(response.getStatusCode() == HttpStatus.OK){
            JsonNode listProdutoJson = null;
            try {
                listProdutoJson = objectMapper.readTree(response.getBody());
                for(JsonNode produtoJson: listProdutoJson){
                    ProdutoEstoqueResponseDTO produto = new ProdutoEstoqueResponseDTO(
                            produtoJson.get("id").asLong(),
                            produtoJson.get("nome").asText(),
                            produtoJson.get("quantidadeEmEstoque").asInt(),
                            produtoJson.get("preco").asDouble()
                    );
                    produtosEstoque.add(produto);
                }

            } catch (JsonProcessingException | RuntimeException e) {
                throw new RuntimeException("Erro ao tratar produtos do estoque", e);
            }

        } else if(response.getStatusCode() == HttpStatus.BAD_REQUEST){
            //TODO throw new RegraNegocioException(reponse.getMessage());
        } else if(response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
            throw new RuntimeException("Erro ao atualizar estoque: " + response.getBody());
        }

        return produtosEstoque;
    }
}
