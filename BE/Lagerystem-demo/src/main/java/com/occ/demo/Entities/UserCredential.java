package com.occ.demo.Entities;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name="usercredentials")
@Data
@NamedQuery(name = UserCredential.FIND_USER_BY_USERNAME, query = "select u from UserCredential u where u.username = :username")
public class UserCredential implements Serializable {


    public static final String FIND_USER_BY_USERNAME = "UserCredentials.findByUsername";

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "username must not be null")
    @NotEmpty(message = "Username must not be empty")
    @Column(name = "username")
    private String username;

    @NotNull(message = "Password must not be null")
    @NotEmpty(message = "Password must not be empty")
    @Column(name = "password")
    private String password;

    private String salt;


}
