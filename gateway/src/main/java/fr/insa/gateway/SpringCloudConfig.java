package fr.insa.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {

    @Autowired
    AuthFilter authFilter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/user/api/v1/userRessources/**")
                        .filters(f -> f.filter(authFilter.apply(new AuthFilter.Config())))
                        .uri("lb://user-client"))
                .route(r -> r.path("/user/api/v1/authorization/**")
                        .uri("lb://user-client"))
                .route(r -> r.path("/restaurant/**")
                        .uri("lb://restaurant-client"))
                .build();
    }
}
