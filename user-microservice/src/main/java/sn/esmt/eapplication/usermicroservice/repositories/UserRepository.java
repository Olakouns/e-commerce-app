package sn.esmt.eapplication.usermicroservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.esmt.eapplication.usermicroservice.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
