package org.chassot.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uid;
    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    private DateTime created;
    private DateTime updated;
    private Boolean active = false;

}
