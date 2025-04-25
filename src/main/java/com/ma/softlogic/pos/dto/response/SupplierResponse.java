package com.ma.softlogic.pos.dto.response;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierResponse {
    private UUID id;
    private String name;
    private String contact;
    private String phone;
    private String email;
    private String address;
    private String status;
}
