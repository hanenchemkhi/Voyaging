package com.perscholas.voyaging.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.perscholas.voyaging.annotation.FieldsValueMatch;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Nationalized;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match!"
)})
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Nationalized
    @NotBlank(message = "Please enter first name")
    @Size(min=2, max= 50, message = "Please enter a valid name")
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    String firstName;
    @Nationalized
    @NotBlank(message = "Please enter last name")
    @Size(min=2, max= 50, message = "Please enter a valid name")
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    String lastName;
    @NotBlank(message = "Please enter email")
    @Column(unique = true)
    @Email(message="Please enter a valid email address")
    String email;
    @NotBlank(message="Password is required")
    @Size(min=8,  message = "Password length must be between 8 and 35 characters (special characters are permitted)")
    String password;

    @NotBlank(message="Confirm password is required")
    @Size(min=8,  message = "Password length must be between 8 and 35 characters (special characters are permitted)")
    @Transient
    String confirmPassword;


    @Enumerated(EnumType.STRING)
    Role role;
    @ManyToMany(cascade =CascadeType.MERGE, fetch = FetchType.EAGER, targetEntity = Authority.class)
    @JoinTable(name = "user_authoritie", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
            @JoinColumn(name = "authoritie_id", referencedColumnName = "id") })
    List<Authority> authorities = new ArrayList<>();

    public User(String password, String confirmPassword, String firstName, String lastName, String email, Role role) {

        this.password = password;
        this.confirmPassword = confirmPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }



    public void addAuthority(Authority auth){
        authorities.add(auth);
    }


    public void removeAuthority(Authority auth ){
        authorities.remove(auth);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }
}
