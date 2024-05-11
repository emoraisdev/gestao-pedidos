package br.com.fiap.mslogistica.integration;

import br.com.fiap.mslogistica.model.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NominationAPI {

    String urlSearch = "https://nominatim.openstreetmap.org/search?";

    public String getLocal(Endereco endereco){
        RestTemplate restTemplate = new RestTemplate();

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

        return restTemplate.getForObject(url.toString(), String.class);
    }
}
