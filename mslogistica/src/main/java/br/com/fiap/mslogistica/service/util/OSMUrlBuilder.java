package br.com.fiap.mslogistica.service.util;

import br.com.fiap.mslogistica.model.Coordenada;
import br.com.fiap.mslogistica.model.Endereco;

import java.util.Optional;

/**
 * Gera a URL do OpenStreetMap com a rota da Entrega.
 *
 */
public class OSMUrlBuilder {

    private static final String BASE =  "https://www.openstreetmap.org/directions?engine=fossgis_osrm_car&route=";

    private static final String ZOOM =  "#map=13";


    public static String build(Coordenada origem, Coordenada destino,
                               Coordenada localEntregador){

         var rota = new StringBuilder(BASE)
                .append(origem.getLatitude()).append("%2C")
                .append(origem.getLongitude()).append("%3B")
                .append(destino.getLatitude()).append("%2C")
                .append(destino.getLongitude());

         if (localEntregador != null) {
             rota.append("&mlat=").append(localEntregador.getLatitude())
                     .append("&mlon=").append(localEntregador.getLongitude());
         }

         return rota.append(ZOOM)
                .append("/").append(origem.getLatitude())
                .append("/").append(origem.getLongitude()).toString();
    }

}
