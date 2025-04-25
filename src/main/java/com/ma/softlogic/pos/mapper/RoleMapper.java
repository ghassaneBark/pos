package com.ma.softlogic.pos.mapper;


import com.ma.softlogic.pos.dto.request.RoleRequest;
import com.ma.softlogic.pos.dto.response.RoleResponse;
import com.ma.softlogic.pos.model.Feature;
import com.ma.softlogic.pos.model.Role;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "features", ignore = true)
    Role toEntity(RoleRequest roleRequest);

    @Mapping(target = "features", expression = "java(mapFeatures(role.getFeatures()))")
    RoleResponse toResponse(Role role);

    default Set<String> mapFeatures(Set<Feature> features) {
        return features.stream()
                .map(Feature::getCode)
                .collect(Collectors.toSet());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "features", ignore = true)
    void updateEntity(RoleRequest roleRequest, @MappingTarget Role role);
}
