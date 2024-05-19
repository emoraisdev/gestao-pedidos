package br.com.fiap.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigGateway {

    @Bean
    public RouteLocator custom(RouteLocatorBuilder builder){
        return builder.routes()
                .route("logistica", r -> r.path("/logistica/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8085"))
                .route("cliente", r -> r.path("/cliente/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8088"))
                .build();

    }
}
