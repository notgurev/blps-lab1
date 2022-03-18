package foxgurev.blps.auth.security.service;

import foxgurev.blps.auth.security.UserDetailsImpl;
import foxgurev.blps.auth.user.User;
import foxgurev.blps.auth.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
        User user = userRepo
                .findByUserLogin(userLogin)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User:" + userLogin + " not found")
                );
        return UserDetailsImpl.build(user);
    }
}
