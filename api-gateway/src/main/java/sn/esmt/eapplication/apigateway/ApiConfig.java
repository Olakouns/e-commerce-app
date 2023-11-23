package sn.esmt.eapplication.apigateway;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("product-microservice", r -> r
                        .path("/api/products-microservice/**", "/api/products/**")
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
                .build();
    }
}
