package com.fitlens.backend.dto.ecercise_video;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object containing exercise video details and the full playback URL")
public class ExerciseVideoResponse {

    @Schema(description = "Video unique identifier", example = "1")
    private Long id;

    @Schema(description = "Target muscle group", example = "BENCH")
    private String muscleGroup;

    @Schema(description = "Name of the exercise", example = "Barbell Bench Press")
    private String exerciseName;

    @Schema(description = "Full, playable URL of the video hosted on Supabase",
            example = "https://rffxjreicblsallttyui.supabase.co/storage/v1/object/public/Bench/bench%20press.mp4")
    private String videoUrl;
}
