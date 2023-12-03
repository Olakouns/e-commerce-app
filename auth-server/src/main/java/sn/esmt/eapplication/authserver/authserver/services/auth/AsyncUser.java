package sn.esmt.eapplication.authserver.authserver.services.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Scheduler;
import sn.esmt.eapplication.authserver.authserver.dto.UserDTO;
import sn.esmt.eapplication.authserver.authserver.dto.UserDTOMicro;

@Service
@RequiredArgsConstructor
public class AsyncUser {


    private final WebClient.Builder webClientBuilder;
    private final Scheduler elasticScheduler;

    @Async
    public void createUserInUserMicroService(UserDTO userDTO) {
        try {
            UserDTOMicro userDTOMicro = UserDTOMicro.builder()
                    .name(userDTO.getName())
                    .email(userDTO.getEmail())
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
