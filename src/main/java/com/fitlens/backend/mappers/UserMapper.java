package com.fitlens.backend.mappers;

import com.fitlens.backend.dto.auth.AuthResponse;
import com.fitlens.backend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

	AuthResponse toResponse(User user);

}
