package com.ma.softlogic.pos.dto.request;


import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierRequest {
    @NotBlank(message = "Supplier name is required")
    private String name;

    private String contact;
    private String phone;
    private String email;
    private String address;
    private String status;
}