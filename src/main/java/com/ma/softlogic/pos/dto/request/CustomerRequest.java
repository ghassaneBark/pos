package com.ma.softlogic.pos.dto.request;


import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "password") // Exclut le mot de passe des logs
public class CustomerRequest {



    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Le nom contient des caractères invalides")
    private String name;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Size(max = 100, message = "L'email ne peut pas dépasser 100 caractères")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, max = 100, message = "Le mot de passe doit contenir entre 8 et 100 caractères")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Le mot de passe doit contenir au moins 1 majuscule, 1 minuscule, 1 chiffre et 1 caractère spécial"
    )
    private String password;

    @Size(max = 200, message = "L'adresse ne peut pas dépasser 200 caractères")
    private String address;

    @Size(max = 50, message = "La ville ne peut pas dépasser 50 caractères")
    private String city;

    @Size(max = 20, message = "Le code postal ne peut pas dépasser 20 caractères")
    private String zipCode;

    @Size(max = 20, message = "Le téléphone ne peut pas dépasser 20 caractères")
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$",
            message = "Numéro de téléphone invalide")
    private String phone;

    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate birthDate;

    @AssertTrue(message = "Vous devez accepter les conditions générales")
    private boolean termsAccepted;

    // Validation personnalisée pour les clients majeurs
    @AssertTrue(message = "Vous devez avoir au moins 18 ans")
    private boolean isAdult() {
        if (birthDate == null) {
            return false;
        }
        return birthDate.plusYears(18).isBefore(LocalDate.now());
    }
}
