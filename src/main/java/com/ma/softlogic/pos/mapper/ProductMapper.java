package com.ma.softlogic.pos.mapper;


import com.ma.softlogic.pos.dto.request.ProductRequest;
import com.ma.softlogic.pos.dto.response.ProductResponse;
import com.ma.softlogic.pos.model.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductRequest productRequest);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    ProductResponse toResponse(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", ignore = true)
    void updateEntity(ProductRequest productRequest, @MappingTarget Product product);
}
