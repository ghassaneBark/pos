package com.ma.softlogic.pos.dto.request;


import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequest {
    @NotBlank(message = "Role name is required")
    private String name;

    private Set<Integer> featureIds;
}