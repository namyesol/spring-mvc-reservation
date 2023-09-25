package kr.or.connect.reservation.repository;

import java.util.Collection;

import kr.or.connect.reservation.domain.user.User;

public interface UserRepository {
    
    User save(User user);

    User findById(Integer id);
    User findByEmail(String email);

    Collection<User> findAll();

    boolean existsById(Integer id);
    boolean existsByEmail(String email);
}
