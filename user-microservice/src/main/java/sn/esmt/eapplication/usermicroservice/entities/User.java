package sn.esmt.eapplication.usermicroservice.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User  {
    @Id
    private long id;

    private String name;

    private String username;

    private String email;
}
