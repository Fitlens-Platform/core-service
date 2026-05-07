package com.fitlens.backend.mappers;

import com.fitlens.backend.dto.customer_history.CustomerHistoryResponse;
import com.fitlens.backend.entities.User;
import com.fitlens.backend.entities.CustomerHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerHistoryMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "user", source = "user")
	CustomerHistory toHistory(User user);

	@Mapping(target = "customerId", source = "user.id")
	CustomerHistoryResponse toResponse(CustomerHistory customerHistory);

}
