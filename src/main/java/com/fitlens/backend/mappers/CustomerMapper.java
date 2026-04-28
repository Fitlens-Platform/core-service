package com.fitlens.backend.mappers;

import com.fitlens.backend.dto.customer.CustomerResponse;
import com.fitlens.backend.dto.customer.CustomerUpdateRequest;
import com.fitlens.backend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {

	@Mapping(target = "workoutPlan", source = "workoutPlan.name")
	CustomerResponse toResponse(User user);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "email", ignore = true)
	void updateCustomer(CustomerUpdateRequest request, @MappingTarget User user);

}
