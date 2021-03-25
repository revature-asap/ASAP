package com.revature.entities;

import com.revature.util.UserRoleConverter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false,unique = true,name = "username")
    private String username;

    @Column(nullable = false,name= "password")
    private String password;

    @Column(nullable = false,unique = true,name = "email")
    private String email;

    @Column(nullable = false,name = "first_name")
    private String firstName;

    @Column(nullable = false,name= "last_name")
    private String lastName;

    @Column(nullable = false, name= "user_role")
    @Convert(converter = UserRoleConverter.class)
    private UserRole role;

    @Column(nullable = false, name ="account_confirmed")
    private boolean accountConfirmed;

    public User() {
        super();
    }

    public User(String username, String password, String email, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(int userId, String username, String password, String email, String firstName, String lastName, UserRole role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isAccountConfirmed() {
        return accountConfirmed;
    }

    public void setAccountConfirmed(boolean accountConfirmed) {
        this.accountConfirmed = accountConfirmed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && accountConfirmed == user.accountConfirmed && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, email, firstName, lastName, role, accountConfirmed);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", accountConfirmed=" + accountConfirmed +
                '}';
    }
}
