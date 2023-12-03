package sn.esmt.eapplication.usermicroservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import sn.esmt.eapplication.usermicroservice.dto.UserDto;
import sn.esmt.eapplication.usermicroservice.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public Mono<UserDto> createUer(@RequestBody UserDto userDto) {
        return userService.createUer(userDto);
    }

    @GetMapping("/{userId}")
    public Mono<UserDto> getUserById(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/customer/{customerId}")
    public Mono<UserDto> getUserByCustomerId(@PathVariable long customerId) {
        return userService.getUserByCustomerId(customerId);
    }
}
