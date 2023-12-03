package sn.esmt.eapplication.authserver.authserver.services.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import sn.esmt.eapplication.authserver.authserver.dto.SignupDTO;
import sn.esmt.eapplication.authserver.authserver.dto.UserDTO;
import sn.esmt.eapplication.authserver.authserver.dto.UserDTOMicro;
import sn.esmt.eapplication.authserver.authserver.entities.User;
import sn.esmt.eapplication.authserver.authserver.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AsyncUser asyncUser;


    @Override
    public UserDTO createUser(SignupDTO signupDTO) {
        User user = new User();
        user.setName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        User createdUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setName(createdUser.getName());

        // TODO: 12/3/2023 Send CREATE USER REQUEST HERE TO USER MICROSERVICE
        asyncUser.createUserInUserMicroService(userDTO);
        return userDTO;
    }
}
