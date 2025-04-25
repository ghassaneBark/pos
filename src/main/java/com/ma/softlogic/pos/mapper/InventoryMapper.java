package com.ma.softlogic.pos.mapper;


import com.ma.softlogic.pos.dto.request.InventoryRequest;
import com.ma.softlogic.pos.dto.response.InventoryResponse;
import com.ma.softlogic.pos.model.Inventory;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    @Mapping(target = "product", ignore = true)
    Inventory toEntity(InventoryRequest inventoryRequest);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    InventoryResponse toResponse(Inventory inventory);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "product", ignore = true)
    void updateEntity(InventoryRequest inventoryRequest, @MappingTarget Inventory inventory);
}
