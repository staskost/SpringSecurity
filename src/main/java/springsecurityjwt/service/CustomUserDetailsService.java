package springsecurityjwt.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springsecurityjwt.repositories.UserRepository;
import springsecurityjwt.model.CustomUserDetails;
import springsecurityjwt.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    return new CustomUserDetails(user);
  }

  private Set<SimpleGrantedAuthority> getAuthority() {
    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
      authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    return authorities;
  }
}
