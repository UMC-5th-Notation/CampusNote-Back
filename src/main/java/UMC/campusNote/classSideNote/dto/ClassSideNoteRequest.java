package UMC.campusNote.classSideNote.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;



@Getter
@Setter
@Builder
public class ClassSideNoteRequest {

    // private Long userLessonId
    @NotBlank
    private String content;

    @NonNull
    private Boolean isTodo;

    @NonNull
    private Integer colorCode;

    private LocalDate deadline;
}