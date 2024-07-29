package org.tn.subscriptiontool.core.models.user;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private LocalDate joinedDate;

    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Transient
    private int age;

    public User() {}

    public User(Long id, boolean enabled, String username, String firstName, String lastName, String email, String password,
                LocalDate dateOfBirth, LocalDate joinedDate) {
        this.id = id;
        this.enabled = enabled;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.joinedDate = joinedDate;
    }

    public User(boolean enabled, String username, String firstName, String lastName, String email, String password,
                LocalDate dateOfBirth, LocalDate joinedDate) {
        this.enabled = enabled;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.joinedDate = joinedDate;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    @Override
    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled;}

    public LocalDate getDateOfBirth() { return dateOfBirth; }

    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public LocalDate getJoinedDate() { return joinedDate; }

    public void setJoinedDate(LocalDate joinedDate) { this.joinedDate = joinedDate; }

    public int getAge() { return Period.between(this.dateOfBirth, LocalDate.now()).getYears(); }

    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", enabled=" + enabled +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", joinedDate=" + joinedDate +
                ", age=" + age +
                '}';
    }
}
