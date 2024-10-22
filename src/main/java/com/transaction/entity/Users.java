package com.transaction.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "USERS")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users extends StatusEntity {

    @Column(
            name = "username",
            length = 1024,
            unique = true,
            nullable = false
    )
    private String username;

    @Column(
            name = "email",
            length = 1024,
            unique = true,
            nullable = false
    )
    private String email;

    private String firstName;
    private String lastName;
    private String displayName;
    private String password;

}
