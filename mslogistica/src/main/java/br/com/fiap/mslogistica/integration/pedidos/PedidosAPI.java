package br.com.fiap.mslogistica.integration.pedidos;

import br.com.fiap.mslogistica.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class PedidosAPI {

    @Value("${api.mspedidos.server}")
    private String urlMSPedidos;

    private static final String API_PEDIDOS = "/api/pedidos/";

    RestTemplate restTemplate;

    public PedidosAPI(){
        restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

                var statusCode = response.getStatusCode();
                if (statusCode == HttpStatus.BAD_REQUEST) {
                    // Tratamento personalizado para código 400
                    throw new BusinessException("Bad Request: " +
                            StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));
                } else {
                    // Tratamento padrão para outros códigos de erro
                    super.handleError(response);
                }
            }
        });
    }

    public Object consultar(Long pedidoId) {

        var retorno = restTemplate.getForObject(urlMSPedidos +
                        API_PEDIDOS + pedidoId,
                String.class);

        return retorno;
    }

    public Object atualizarStatus(Long pedidoId, Integer status) {

        // Cria o objeto de requisição
        StatusUpdateRequest request = new StatusUpdateRequest(status);

        // Configura os headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Cria a entidade HTTP com o corpo e headers
        HttpEntity<StatusUpdateRequest> entity = new HttpEntity<>(request, headers);

        // Faz a requisição PUT para atualizar o status
        ResponseEntity<String> response = restTemplate.exchange(
                urlMSPedidos + API_PEDIDOS +
                "atualiza-status/"+ pedidoId,
                HttpMethod.PUT,
                entity,
                String.class
        );

        return response.getBody();
    }
}
