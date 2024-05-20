package br.com.fiap.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigGateway {

    @Value("${api.mslogistica.server}")
    private String mslogistica;

    @Value("${api.msclientes.server}")
    private String msclientes;

    @Value("${api.msprodutos.server}")
    private String msprodutos;

    @Value("${api.mscargaprodutos.server}")
    private String mscargaprodutos;

    @Value("${api.mspedidos.server}")
    private String mspedidos;

    @Bean
    public RouteLocator custom(RouteLocatorBuilder builder){
        return builder.routes()
                .route("logistica", r -> r.path("/logistica/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri(mslogistica))
                .route("cliente", r -> r.path("/cliente/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri(msclientes))
                .route("produtos", r -> r.path("/produtos/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri(msprodutos))
                .route("cargaprodutos", r -> r.path("/cargaprodutos/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri(mscargaprodutos))
                .route("pedidos", r -> r.path("/pedidos/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri(mspedidos))
                .build();

    }
}
