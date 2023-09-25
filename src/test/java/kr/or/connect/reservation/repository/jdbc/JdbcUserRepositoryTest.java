package kr.or.connect.reservation.repository.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.domain.user.Role;
import kr.or.connect.reservation.domain.user.User;
import kr.or.connect.reservation.repository.UserRepository;


public class JdbcUserRepositoryTest extends AbstractJdbcRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    public void shouldSaveUser() {
        // given
        User user = new User("test@connect.or.kr", "test", "test", "test");
        user.addRole(new Role("ROLE_USER"));
        user.addRole(new Role("ROLE_ADMIN"));

        // when
        userRepository.save(user);

        // then
        User savedUser = userRepository.findById(user.getId());
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getRoles()).hasSize(2);
        assertThat(savedUser.getRoles()).contains(new Role("ROLE_USER"), new Role("ROLE_ADMIN"));
        assertThat(new ArrayList<>(savedUser.getRoles()).get(0).getId()).isNotNull();
    }

    @Test
    public void shouldFindById() {
        User user = userRepository.findById(1);
        assertThat(user).hasNoNullFieldsOrProperties();
        ArrayList<Role> roles = new ArrayList<>(user.getRoles());
        assertThat(roles).hasSize(2);
        roles.stream().map(role -> assertThat(role).hasNoNullFieldsOrProperties());
    }

    @Test
    public void shouldFindByEmail() {
        User user = userRepository.findByEmail("carami@connect.co.kr");
        assertThat(user).hasNoNullFieldsOrProperties();
        ArrayList<Role> roles = new ArrayList<>(user.getRoles());
        assertThat(roles).hasSize(2);
        roles.stream().map(role -> assertThat(role).hasNoNullFieldsOrProperties());
    }

    @Test
    public void shouldExistsById() {
        boolean exists = userRepository.existsById(1);
        assertThat(exists).isTrue();
    }

    @Test
    public void shouldNotExistsById() {
        boolean existsById = userRepository.existsById(0);
        assertThat(existsById).isFalse();
    }

    @Test
    public void shouldExistsByEmail() {
        boolean exists = userRepository.existsByEmail("carami@connect.co.kr");
        assertThat(exists).isTrue();
    }

    @Test
    public void shouldNotExistsByEmail() {
        boolean exists = userRepository.existsByEmail("noexists@@connect.co.kr");
        assertThat(exists).isFalse();
    }
}
