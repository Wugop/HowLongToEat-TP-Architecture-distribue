package fr.insa.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.reactive.AbstractServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Missing auth information");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            String[] parts = authHeader.split(" ");

            if (parts.length != 2 || !"Bearer".equals((parts[0]))) {
                throw new RuntimeException("Incorrect auth Structure");
            }


            webClientBuilder.build()
                    .get()
                    .uri("http://user-client/user/api/v1/authorization/is-authorized?jwt=" + parts[1])
                    .retrieve().bodyToMono(String.class)
                    .filter(token -> !token.equals("-1"))
                    .blockOptional()
                    .map(id -> {
                        exchange.getRequest()
                                .mutate()
                                .header("x-auth-user-id", id);
                        return exchange;
                    })
                    .orElse(onError(exchange,"error"));

            return webClientBuilder.build()
                    .get()
                    .uri("http://user-client/user/api/v1/authorization/is-authorized?jwt=" + parts[1])
                    .retrieve().bodyToMono(String.class)
                    .filter(token -> !token.equals("-1"))
                    .doOnError(token -> !token.equals("-1"))
                    .onErrorReturn("error"))
                    .map(id -> {
                if (id.equals("-1")) { //Si id = -1, alors le token a mal été généré
                    exchange.getRequest().mutate().build();

                    //Trouver comment modifier la requete afin de la mener vers une page erreur

                            /*ServerHttpRequest request = new ServletServerHttpRequest();
                            exchange.mutate().request(exchange.getRequest().mutate().path("/user/api/v1/authorization/not-authorized").build());*/
                } else { //Sinon, cela veut dire que le token a bien été généré
                    exchange.getRequest()
                            .mutate()
                            .header("x-auth-user-id", id);
                }
                return exchange;
            }).flatMap(chain::filter);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return response.writeWith(Flux.just(buffer));
    }


    public static class Config {

    }
}

