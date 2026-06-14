package com.fitlens.backend.mappers;

import com.fitlens.backend.dto.ecercise_video.ExerciseVideoResponse;
import com.fitlens.backend.entities.ExerciseVideo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Value;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ExerciseVideoMapper {


    @Mapping(target = "videoUrl", expression = "java((baseUrl + entity.getVideoPath()).replace(\"=\", \"\"))")
    public abstract ExerciseVideoResponse toDto(ExerciseVideo entity, String baseUrl);
}
