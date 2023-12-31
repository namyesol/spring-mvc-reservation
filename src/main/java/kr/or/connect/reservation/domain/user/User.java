package kr.or.connect.reservation.domain.user;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class User {
    
    private Integer id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Set<Role> roles;

    public User() {}

    public User(String email, String password, String name, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.createDate = LocalDateTime.now();
        this.modifyDate = this.createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Set<Role> getRoles() {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        if (!this.getRoles().contains(role)) {
            this.getRoles().add(role);
        }
    }
}
