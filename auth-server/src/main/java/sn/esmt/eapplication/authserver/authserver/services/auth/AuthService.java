package sn.esmt.eapplication.authserver.authserver.services.auth;


import sn.esmt.eapplication.authserver.authserver.dto.SignupDTO;
import sn.esmt.eapplication.authserver.authserver.dto.UserDTO;

public interface AuthService {
    UserDTO createUser(SignupDTO signupDTO);
}
