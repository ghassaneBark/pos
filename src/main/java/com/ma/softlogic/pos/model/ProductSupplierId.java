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
public class ProductSupplierId implements Serializable {

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "supplier_id")
    private UUID supplierId;
}

