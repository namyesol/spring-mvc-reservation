package kr.or.connect.reservation.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import kr.or.connect.reservation.domain.user.Role;
import kr.or.connect.reservation.domain.user.User;

public class JdbcUserExtractor extends OneToManyResultSetExtractor<User, Role, Integer>{

    public JdbcUserExtractor() {
        super(new JdbcUserRowMapper(), new JdbcUserRoleRowMapper());
    }

    @Override
    protected Integer mapPrimaryKey(ResultSet rs) throws SQLException {
        return rs.getInt("u.id");
    }

    @Override
    protected Integer mapForeignKey(ResultSet rs) throws SQLException {
        if (rs.getObject("role_user_id") == null) {
            return null;
        }
        return rs.getInt("role_user_id");
    }

    @Override
    protected void addChild(User root, Role child) {
        root.addRole(child);
    }
    
    private static class JdbcUserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("u.id"));
            user.setEmail(rs.getString("u.email"));
            user.setPassword(rs.getString("u.password"));
            user.setName(rs.getString("u.name"));
            user.setPhone(rs.getString("u.phone"));
            user.setCreateDate(rs.getObject("u.create_date", Timestamp.class).toLocalDateTime());
            user.setModifyDate(rs.getObject("u.modify_date", Timestamp.class).toLocalDateTime());
            return user;
        }
    }

    private static class JdbcUserRoleRowMapper implements RowMapper<Role> {
        @Override
        public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
            Role role = new Role();
            role.setId(rs.getInt("role_id"));
            role.setName(rs.getString("role_name"));
            return role;
        }
    }
}
