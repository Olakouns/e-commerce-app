package sn.esmt.eapplication.usermicroservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import sn.esmt.eapplication.usermicroservice.dto.UserDto;
import sn.esmt.eapplication.usermicroservice.entities.Customer;
import sn.esmt.eapplication.usermicroservice.entities.User;
import sn.esmt.eapplication.usermicroservice.repositories.CustomerRepository;
import sn.esmt.eapplication.usermicroservice.repositories.UserRepository;
import sn.esmt.eapplication.usermicroservice.service.UserService;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final Scheduler elasticScheduler;


    @Override
    public Mono<UserDto> createUer(UserDto userDto) {

        return Mono.fromCallable(() -> {
            User user = User.builder()
                    .email(userDto.getEmail())
                    .name(userDto.getName())
                    .username(userDto.getUsername())
                    .id(userDto.getUserId())
                    .build();

            Customer customer = Customer.builder()
                    .firstName(userDto.getFirstName())
                    .lastName(userDto.getLastName())
                    .email(userDto.getEmail())
                    .phone(userDto.getPhone())
                    .address(userDto.getAddress())
                    .user(user)
                    .build();

            Customer customerDB = customerRepository.save(customer);

            userDto.setUserId(customerDB.getUser().getId());
            userDto.setCustomerId(customerDB.getId());
            return userDto;
        }).subscribeOn(elasticScheduler);


    }

    @Override
    public Mono<UserDto> getUserById(long userId) {
        return Mono.fromCallable(() -> {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            Customer customer = customerRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Customer not found"));
            return UserDto.builder()
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .email(customer.getEmail())
                    .phone(customer.getPhone())
                    .address(customer.getAddress())
                    .name(user.getName())
                    .username(user.getUsername())
                    .userId(user.getId())
                    .customerId(customer.getId())
                    .build();
        }).subscribeOn(elasticScheduler);
    }

    @Override
    public Mono<UserDto> getUserByCustomerId(long customerId) {
        return Mono.fromCallable(() -> {
            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
            return UserDto.builder()
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .email(customer.getEmail())
                    .phone(customer.getPhone())
                    .address(customer.getAddress())
                    .name(customer.getUser().getName())
                    .username(customer.getUser().getUsername())
                    .userId(customer.getUser().getId())
                    .customerId(customer.getId())
                    .build();
        }).subscribeOn(elasticScheduler);
    }
}
