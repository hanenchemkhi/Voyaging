package com.perscholas.voyaging.model;
import jakarta.persistence.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;

    @NotBlank(message = "Please enter street")
    @Size(max=150, message = "Invalid street address")
    String street;
    @NotBlank(message = "Please enter city")
    @Size(max=50, message = "Invalid city")
    String city;
    @NotBlank(message = "Please enter country")
    @Size(max=50, message = "Invalid city")
    String country;
    @NotBlank(message= "Please enter zip code")
    @Pattern(regexp="(^$|[0-9]{5})",message = "Zip Code must be 5 digits")
    String zipCode;

    @OneToOne(mappedBy = "address")
    Customer customer;

}
