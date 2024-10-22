package com.transaction.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPojo {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String token;

    public UserPojo(String firstName, String lastName, String phoneNumber, String email, String token){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.token = token;
    }
}
