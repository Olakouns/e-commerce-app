package sn.esmt.eapplication.apigateway.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sn.esmt.eapplication.apigateway.validators.RouteValidator;

//@Component
public class AuthenticationFilterV2 extends AbstractGatewayFilterFactory<AuthenticationFilterV2.Config> {

    //@Autowired
    private RouteValidator routeValidator;
   // @Autowired
    private RestTemplate template;

    //@Value("${identity.server}")
    private String authServer;

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
