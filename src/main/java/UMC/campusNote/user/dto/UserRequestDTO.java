package UMC.campusNote.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDTO {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class AttendedSemesterUpdateDTO{

        @Schema(description = "수정할 수강 학기", example = "4-2")
        @NotNull
        String attendedSemester;
    }
}
