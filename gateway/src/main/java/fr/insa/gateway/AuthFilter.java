package fr.insa.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

            return webClientBuilder.build()
                    .get()
                    .uri("http://user-client/user/api/v1/authorization/is-authorized?jwt=" + parts[1])
                    .retrieve().bodyToMono(String.class)
                    .filter(token -> !token.equals("-1"))
                    .map(id1 -> {
                        exchange.getRequest()
                                .mutate()
                                .header("x-auth-user-id", id1);
                        return exchange;
                    })
                    .switchIfEmpty(Mono.defer(() -> setErrorResponse(exchange).then(Mono.empty())))
                    .flatMap(chain::filter);
        };
    }

    private Mono<Void> setErrorResponse(ServerWebExchange serverHttpResponse) {
        ServerHttpResponse response = serverHttpResponse.getResponse();
        response.setStatusCode(HttpStatus.EXPECTATION_FAILED);
        byte[] bytes = "Not Authorized!".getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = serverHttpResponse.getResponse().bufferFactory().wrap(bytes);
        return response.writeWith(Flux.just(buffer));
    }


    public static class Config {

    }
}

