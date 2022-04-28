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
                        .filters(f -> f.addResponseHeader("Access-Control-Allow-Origin", "*"))
                        .uri("lb://user-client"))
                .route(r -> r.path("/user/api/v1/userRessourcesRestricted/**")
                        .filters(f -> {
                            f.filter(authFilter.apply(new AuthFilter.Config()));
                            f.addResponseHeader("Access-Control-Allow-Origin", "*");
                            return f;
                        })
                        .uri("lb://user-client"))

                .route(r -> r.path("/user/api/v1/authorization/**")
                        .filters(f -> {
                            f.addResponseHeader("Access-Control-Allow-Origin", "*");
                            f.addRequestHeader("Access-Control-Allow-Origin", "*");
                            f.addRequestHeader("Access-Control-Allow-Methods","POST");
                            f.addResponseHeader("Access-Control-Allow-Methods","POST");
                            return f;
                        })
                        .uri("lb://user-client"))

                .route(r -> r.path("/restaurant/**")
                        .filters(f -> f.addResponseHeader("Access-Control-Allow-Origin", "*"))
                        .uri("lb://restaurant-client"))

                .route(r -> r.path("/note/api/v1/noteRessources/**")
                        .filters(f -> f.addResponseHeader("Access-Control-Allow-Origin", "*"))
                        .uri("lb://note-client"))

                .route(r -> r.path("/note/api/v1/noteRessourcesRestricted/**")
                        .filters(f -> {
                            f.filter(authFilter.apply(new AuthFilter.Config()));
                            f.addResponseHeader("Access-Control-Allow-Origin", "*");
                            return f;
                        })
                        .uri("lb://note-client"))
                .build();
    }
}
