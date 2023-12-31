package sn.esmt.eapplication.authserver.authserver.services.auth;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import sn.esmt.eapplication.authserver.authserver.dto.UserDTO;
import sn.esmt.eapplication.authserver.authserver.dto.UserDTOMicro;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncUser {


    private final WebClient.Builder webClientBuilder;
    private final Scheduler elasticScheduler;

    @Async
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallback")
    public void createUserInUserMicroService(UserDTO userDTO) {
        UserDTOMicro userDTOMicro = UserDTOMicro.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .userId(userDTO.getId())
                .build();


        // Make the POST request to the user microservice
        UserDTOMicro response = webClientBuilder
                .build()
                .post()
                .uri("http://user-microservice/api/users/create")
                .bodyValue(userDTOMicro)
                .retrieve()
                .bodyToMono(UserDTOMicro.class)
                .block();
    }

    private void fallback(UserDTO userDTO, Exception e) {
        log.error("Error occurred while calling user microservice", e);
    }
}
