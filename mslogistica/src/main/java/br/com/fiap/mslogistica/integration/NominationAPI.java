package br.com.fiap.mslogistica.integration;

import br.com.fiap.mslogistica.model.Coordenada;
import br.com.fiap.mslogistica.model.Endereco;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NominationAPI {

    String urlSearch = "https://nominatim.openstreetmap.org/search?";

    public Endereco definirLocal(Endereco endereco) throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();

        var retorno = restTemplate.getForObject(getUrl(endereco), String.class);

        JsonNode jsonNode = new ObjectMapper().readTree(retorno);

        endereco.setCoordenada(
                new Coordenada(jsonNode.get(0).get("lat").asText(),
                        jsonNode.get(0).get("lon").asText())
        );

        return endereco;
    }

    private String getUrl(Endereco endereco) {

        var url = new StringBuilder(urlSearch);
        url.append("country=Brasil");

        if (endereco.getCidade() != null) {
            url.append("&city=" + endereco.getCidade());
        }

        if (endereco.getEstado() != null) {
            url.append("&state=" + endereco.getEstado());
        }

        if (endereco.getRua() != null && endereco.getNumero() != null) {
            url.append("&street=" + endereco.getRua() + " "+ endereco.getNumero());
        }

        url.append("&format=jsonv2&limit=1");
        return url.toString();
    }
}
