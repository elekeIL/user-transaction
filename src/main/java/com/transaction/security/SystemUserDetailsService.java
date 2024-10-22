package com.transaction.security;

import com.transaction.repository.UserRepository;
import com.transaction.entity.Users;
import com.transaction.enums.GenericStatus;
import com.transaction.utils.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@RequiredArgsConstructor
public class SystemUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<Users> user = userRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatus.ACTIVE);
    if (user.isEmpty()) {
      throw new ErrorResponse(HttpStatus.BAD_REQUEST, "User details not found");
    }

    return new ApplicationUserDetails(user.get());

  }
}
