package org.example.waste_manager_service.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
@Entity
@Table(name = "waste_center_authorization")
public class WasteCenterAuthorizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String authorizationNumber;

    @PrePersist
    private void prePersist(){
        String numbers = "123456789";
        int length = 20;
        int numberLength = numbers.length();
        Random random = new Random();
        authorizationNumber = "";

        for (int i = 0; i < length; i++)
            authorizationNumber += numbers.charAt(random.nextInt(numberLength));
    }
}