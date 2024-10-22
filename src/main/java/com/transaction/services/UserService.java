package com.transaction.services;

import com.transaction.dto.NewUserDto;
import com.transaction.pojo.UserPojo;

public interface UserService {

    UserPojo createUser(NewUserDto newUserDto);
}
