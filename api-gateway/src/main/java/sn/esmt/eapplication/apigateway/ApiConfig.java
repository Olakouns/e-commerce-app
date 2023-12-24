package sn.esmt.eapplication.apigateway;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import sn.esmt.eapplication.apigateway.filters.AuthenticationFilter;

@Configuration
public class ApiConfig {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("product-microservice", r -> r
                        .path("/api/products-microservice/**", "/api/products/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://product-microservice"))
                .route("discovery-server", r -> r
                        .path("/eureka/web")
                        .filters(f -> f.setPath("/"))
                        .uri("lb://discovery-server"))
                .route("discovery-server-static", r -> r
                        .path("/eureka/**")
                        .uri("lb://discovery-server"))
                .route("user-microservice", r -> r
                        .path("/api/users-microservice/**", "/api/users/**")
                        .uri("lb://user-microservice"))
                .route("auth-server", r -> r
                        .path("/auth/**")
                        .uri("lb://auth-server"))
                .route("order-microservice", r -> r
                        .path("/api/order/**")
                        .uri("lb://order-microservice"))
                .route("user-microservice", r -> r
                        .path("/api/users/**")
                        .uri("lb://user-microservice"))
                .build();
    }
}
