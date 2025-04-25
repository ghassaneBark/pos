package com.ma.softlogic.pos.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoleFeature implements Serializable {
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "feature_id")
    private Integer featureId;
}
