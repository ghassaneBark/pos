package com.ma.softlogic.pos.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductSupplier implements Serializable {
    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "supplier_id")
    private UUID supplierId;



}
