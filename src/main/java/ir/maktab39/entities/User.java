package ir.maktab39.entities;

import ir.maktab39.base.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class User extends BaseEntity<Long> {

    @Column(unique = true, nullable = false)
    private String username;
    private String nationalCode;
    private Date birthday;
    @Column(nullable = false)
    private String password;
    @ManyToOne(cascade = CascadeType.ALL)
    private Role role;

    @Override
    public String toString() {
        return "id=" + id +
                "\n" + "username=" + username +
                "\n" + "nationalCode=" + nationalCode +
                "\n" + "birthday=" + birthday +
                "\n" + "author=" + (role != null ? role.getTitle() : "");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
