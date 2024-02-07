package UMC.campusNote.classSideNote.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ClassSideNoteResponse {

    @NonNull
    private Long id;

    @NonNull
    private String content;

    @NonNull
    private Boolean isTodo;

    @NonNull
    private Integer colorCode;

    private String deadline;

}