package se.ifmo.blos.lab2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.ifmo.blos.lab2.repositories.UserRepository;

import static java.lang.String.format;

@Service("userService")
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    protected UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(
                format("Username with email %s was not found.", username))
        );
    }
}
