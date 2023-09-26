package kr.or.connect.reservation.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.domain.user.Role;
import kr.or.connect.reservation.domain.user.User;
import kr.or.connect.reservation.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createUser(User user) {
        boolean existsByEmail = userRepository.existsByEmail(user.getEmail());
        if (existsByEmail) {
            throw new DuplicateKeyException("email");
        }
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.addRole(new Role("ROLE_USER"));
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return user;
    }
    
}
