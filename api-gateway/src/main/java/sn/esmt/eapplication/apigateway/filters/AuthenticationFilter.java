package sn.esmt.eapplication.apigateway.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import sn.esmt.eapplication.apigateway.validators.RouteValidator;

@Component
@RefreshScope
public class AuthenticationFilter implements GatewayFilter {

    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${identity.server}")
    private String authServer;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = "";
        ServerHttpRequest request = exchange.getRequest();

        if (routeValidator.isSecured.test(request)) {
            if (this.isAuthMissing(request)) {
                return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
            }

            token = this.getAuthHeader(request);

            token = token.substring(7);

            return webClientBuilder
                    .build()
                    .get()
                    .uri("http://" + authServer + "/auth/validate?token=" + token)
                    .retrieve()
                    .bodyToMono(Boolean.class).flatMap(isValid -> {
                        if (Boolean.TRUE.equals(isValid)) {
                            return chain.filter(exchange);
                        } else {
                            return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
                        }
                    });
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String errorMessage = "{\"error\": \"" + err + "\"}";
        return response.writeWith(Mono.just(response.bufferFactory().wrap(errorMessage.getBytes())));
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}
