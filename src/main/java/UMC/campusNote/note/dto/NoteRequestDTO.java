package UMC.campusNote.note.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



public class NoteRequestDTO {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class NoteCreateDTO{
        @Schema(description = "레슨아이디", example = "1")
        @NotNull
        Long lessonId;

        @Schema(description = "현재 학기", example = "2024-1")
        @NotNull
        String semester;

        @Schema(description = "노트이름", example = "노트이름")
        @NotNull
        String noteName;
    }
}
