package UMC.campusNote.classSideNote.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;


public class ClassSideNoteRequestDTO {
    @Getter
    @Setter
    @Builder
    public static class ClassSideNoteCreateDTO {
        @NotBlank
        private String content;

        @NonNull
        private Boolean isTodo;

        @NonNull
        @Min(0x000000)
        @Max(0xffffff)
        private Integer colorCode;

        private LocalDate deadline;
    }

    @Getter
    @Setter
    @Builder
    public static class ClassSideNoteUpdateDTO {
        @NonNull
        private String content;

        private LocalDate deadline;
    }
}