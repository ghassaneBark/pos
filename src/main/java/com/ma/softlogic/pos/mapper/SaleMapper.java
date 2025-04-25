package com.ma.softlogic.pos.mapper;


import com.ma.softlogic.pos.dto.request.SaleRequest;
import com.ma.softlogic.pos.dto.response.SaleResponse;
import com.ma.softlogic.pos.model.Sale;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "total", ignore = true)
    Sale toEntity(SaleRequest saleRequest);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    SaleResponse toResponse(Sale sale);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "total", ignore = true)
    void updateEntity(SaleRequest saleRequest, @MappingTarget Sale sale);
}