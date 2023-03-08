package com.perscholas.voyaging.model;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Nationalized;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Nationalized
    @NotBlank(message = "Please enter first name")
    @Size(min=2, max= 50, message = "Please enter a valid name")
     String firstName;
    @Nationalized
    @NotBlank(message = "Please enter last name")
    @Size(min=2, max= 50, message = "Please enter a valid name")
     String lastName;
    @NotBlank(message = "Please enter email")
    @Column(unique = true)
    @Email(message="Please enter a valid email address")
    String email;
    @NotBlank(message="Password is required)")
    @Size(min=8,  message = "Password length must be between 8 and 35 characters (special characters are permitted)")
    String password;

    @Transient
    String confirmPassword;


    @Enumerated(EnumType.STRING)
    UserType userType;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "users_authorities", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
            @JoinColumn(name = "authoritie_id", referencedColumnName = "id") })
    Set<Authority> authorities = new HashSet<>();

    public User( String password, String confirmPassword, String firstName, String lastName, String email,UserType userType) {

        this.password = setPassword(password);
        this.confirmPassword = confirmPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userType = userType;
    }
    public String setPassword(String password) {
        return this.password = new BCryptPasswordEncoder().encode(password);
    }


    public void addAuthority(Authority auth){
        authorities.add(auth);
    }


}
