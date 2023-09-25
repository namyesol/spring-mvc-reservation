package kr.or.connect.reservation.repository.jdbc;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.domain.user.Role;
import kr.or.connect.reservation.domain.user.User;
import kr.or.connect.reservation.repository.UserRepository;

@Repository
public class JdbcUserRepository implements UserRepository {
    
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertUser;
    private final SimpleJdbcInsert insertRole;

    public JdbcUserRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        this.insertUser = new SimpleJdbcInsert(dataSource)
            .withTableName("user")
            .usingGeneratedKeyColumns("id");

        this.insertRole = new SimpleJdbcInsert(dataSource)
            .withTableName("user_role")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public User save(User user) {
        
        Integer userId = this.insertUser.executeAndReturnKey(createUserParameterSource(user)).intValue();
        user.setId(userId);

        Set<Role> roles = user.getRoles();

        for (Role role : roles) {
            Integer roleId = this.insertRole.executeAndReturnKey(createUserRoleParameterSource(userId, role)).intValue();
            role.setId(roleId);
        }
        return user;
    }

    private MapSqlParameterSource createUserParameterSource(User user) {
        return new MapSqlParameterSource()
        .addValue("email", user.getEmail())
        .addValue("name",  user.getName())
        .addValue("password", user.getPassword())
        .addValue("phone", user.getPhone())
        .addValue("create_date", Timestamp.valueOf(user.getCreateDate()))
        .addValue("modify_date", Timestamp.valueOf(user.getModifyDate()));
    }

    private MapSqlParameterSource createUserRoleParameterSource(Integer userId,  Role role) {
        return new MapSqlParameterSource()
            .addValue("user_id", userId)
            .addValue("role_name", role.getName());
    }

    @Override
    public User findById(Integer id) {
        List<User> users = this.namedParameterJdbcTemplate.query(
            "SELECT u.id, u.name, u.password, u.email, u.phone, u.create_date, u.modify_date, " +
            "r.id as role_id, r.user_id as role_user_id, r.role_name as role_name " +
            "FROM user u LEFT OUTER JOIN user_role r ON u.id = r.user_id " +
            "WHERE u.id = :id " +
            "ORDER BY role_user_id",
            Collections.singletonMap("id", id),
            new JdbcUserExtractor());
        return users.stream().findFirst().orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        List<User> users = this.namedParameterJdbcTemplate.query(
            "SELECT u.id, u.name, u.password, u.email, u.phone, u.create_date, u.modify_date, " +
            "r.id as role_id, r.user_id as role_user_id, r.role_name as role_name " +
            "FROM user u LEFT OUTER JOIN user_role r ON u.id = r.user_id " +
            "WHERE u.email = :email " +
            "ORDER BY role_user_id",
            Collections.singletonMap("email", email),
            new JdbcUserExtractor());
        return users.stream().findFirst().orElse(null);
    }

    @Override
    public Collection<User> findAll() {
        List<User> users = this.namedParameterJdbcTemplate.query(
            "SELECT u.id, u.name, u.password, u.email, u.phone, u.create_date, u.modify_date, " +
            "r.id as role_id, r.user_id as role_user_id, r.role_name as role_name " +
            "FROM user u LEFT OUTER JOIN user_role r ON u.id = r.user_id " +
            "ORDER BY role_user_id",
            EmptySqlParameterSource.INSTANCE,
            new JdbcUserExtractor());
        return users;
    }

    @Override
    public boolean existsById(Integer id) {
        List<Integer> ids = this.namedParameterJdbcTemplate.queryForList(
            "SELECT id FROM user WHERE id=:id limit 1",
                Collections.singletonMap("id", id),
          Integer.class);
        return !ids.isEmpty();
    }

    @Override
    public boolean existsByEmail(String email) {
        List<Integer> ids = this.namedParameterJdbcTemplate.queryForList(
            "SELECT id FROM user WHERE email=:email limit 1",
                Collections.singletonMap("email", email),
          Integer.class);
        return !ids.isEmpty();
    }

}
