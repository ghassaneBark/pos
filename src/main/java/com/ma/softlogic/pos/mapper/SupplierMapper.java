package com.ma.softlogic.pos.mapper;



import com.ma.softlogic.pos.dto.request.SupplierRequest;
import com.ma.softlogic.pos.dto.response.SupplierResponse;
import com.ma.softlogic.pos.model.Supplier;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier toEntity(SupplierRequest supplierRequest);
    SupplierResponse toResponse(Supplier supplier);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(SupplierRequest supplierRequest, @MappingTarget Supplier supplier);
}