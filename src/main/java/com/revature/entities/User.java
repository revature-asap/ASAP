package com.revature.entities;

import com.revature.util.UserRoleConverter;

import javax.persistence.*;

import java.util.List;
import java.util.Objects;

/**
 * This the model class for the user with annotations that maps to the database
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * The serial id of the user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    /**
     * The username of the user
     */
    @Column(nullable = false,unique = true,name = "username")
    private String username;

    /**
     * The password of the user
     */
    @Column(nullable = false,name= "password")
    private String password;

    /**
     * The email of the user
     */
    @Column(nullable = false,unique = true,name = "email")
    private String email;

    /**
     * The firstname of the user
     */
    @Column(nullable = false,name = "first_name")
    private String firstName;

    /**
     * The lastname of the user
     */
    @Column(nullable = false,name= "last_name")
    private String lastName;

    /**
     * The role of the user. It uses the UserRoleConverter class to convert the string into UserRole or vice versa
     */
    @Column(nullable = false, name= "user_role")
    @Convert(converter = UserRoleConverter.class)
    private UserRole role;

    /**
     * Boolean value of the account confirmed. True if the account has been activated else false
     */
    @Column(nullable = false, name ="account_confirmed")
    private boolean accountConfirmed;

    /**
     * Holds a list of Assets that the User has on their watchlist
     */
    @ManyToMany
    @JoinTable(
        name = "user_assets",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "asset_id")
    )
    private List<Asset> watchlist;

    public User() {
        super();
    }

    /**
     * Constructor for initializing username, password, email, firstname, and lastname
     * @param username the username of the user
     * @param password the password of the user
     * @param email the email of the user
     * @param firstName the firstname of the user
     * @param lastName the lastname of the user
     */
    public User(String username, String password, String email, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Constructor for initializing user's id, username, password, email, firstname, lastname, and role
     * @param userId the user id
     * @param username the username of the user
     * @param password the password of the user
     * @param email email of the user
     * @param firstName the firstname of the user
     * @param lastName the lastname of the user
     * @param role the role of the user
     */
    public User(int userId, String username, String password, String email, String firstName, String lastName, UserRole role) {
        this(username, password, email, firstName, lastName);
        this.userId = userId;
        this.role = role;
    }

    /**
     * Constructor for initializing user's id, username, password, email, firstname, lastname, and role
     * @param userId the user id
     * @param username the username of the user
     * @param password the password of the user
     * @param email email of the user
     * @param firstName the firstname of the user
     * @param lastName the lastname of the user
     * @param role the role of the user
     * @param watchlist the watchlist for this user
     */
    public User(int userId, String username, String password, String email, String firstName, String lastName, UserRole role, List<Asset> watchlist) {
        this(userId, username, password, email, firstName, lastName, role);
        this.watchlist = watchlist;
    }

    /**
     * Copy constructor
     * @param user
     */
    public User(User user) {
        this(user.userId, user.username, user.password, user.email, user.firstName, user.lastName, user.role, user.watchlist);
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

    public List<Asset> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(List<Asset> watchlist) {
        this.watchlist = watchlist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && accountConfirmed == user.accountConfirmed && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && role == user.role && watchlist.equals(user.watchlist);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (accountConfirmed ? 1231 : 1237);
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + userId;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((watchlist == null) ? 0 : watchlist.hashCode());
        return result;
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
                ", watchlist=" + watchlist +
                '}';
    }
}
