package com.ma.softlogic.pos.mapper;


import com.ma.softlogic.pos.dto.request.CategoryRequest;
import com.ma.softlogic.pos.dto.response.CategoryResponse;
import com.ma.softlogic.pos.model.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequest categoryRequest);
    CategoryResponse toResponse(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(CategoryRequest categoryRequest, @MappingTarget Category category);
}
