package sn.esmt.eapplication.usermicroservice.service;

import reactor.core.publisher.Mono;
import sn.esmt.eapplication.usermicroservice.dto.UserDto;

public interface UserService {
    Mono<UserDto> createUer(UserDto userDto);

    Mono<UserDto> getUserById(long userId);

    Mono<UserDto> getUserByCustomerId(long customerId);
}
