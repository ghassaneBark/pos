package com.ma.softlogic.pos.model;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.util.UUID;

import java.io.Serializable;
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserRoleId implements Serializable {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "role_id")
    private Integer roleId;
}
