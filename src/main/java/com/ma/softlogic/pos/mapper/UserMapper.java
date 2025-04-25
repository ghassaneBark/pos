package com.ma.softlogic.pos.mapper;


import com.ma.softlogic.pos.dto.request.UserRequest;
import com.ma.softlogic.pos.dto.response.UserResponse;
import com.ma.softlogic.pos.model.Role;
import com.ma.softlogic.pos.model.User;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserRequest userRequest);

    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    UserResponse toResponse(User user);

    default Set<String> mapRoles(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateEntity(UserRequest userRequest, @MappingTarget User user);
}