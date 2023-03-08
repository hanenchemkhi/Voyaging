package com.perscholas.voyaging.model;


import jakarta.persistence.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Nationalized;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Admin extends User{




    public Admin(
                 @NotBlank(message = "Please enter first name") @Size(min = 2, max = 50, message = "Please enter a valid name") String firstName,
                 @NotBlank(message = "Please enter last name") @Size(min = 2, max = 50, message = "Please enter a valid name") String lastName,
                 @NotBlank(message = "Please enter email") @Email(message = "Please enter a valid email address") String email,
                 @NotBlank(message = "Password is required)") @Size(min = 8, max = 35, message = "Password length must be between 8 and 35 characters (special characters are permitted)") String password,
                 @NotBlank(message = "Confirm Password must not be blank") @Size(min = 8, max = 35, message = "Passwords didn’t match") String confirmPassword,
                 UserType userType

                 ) {
        super( password, confirmPassword, firstName, lastName, email, userType);
    }
}
