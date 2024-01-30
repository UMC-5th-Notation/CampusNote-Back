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

    private Integer errorFlag;


    public ClassSideNoteResponse(@NonNull Long id, @NonNull String content, @NonNull Boolean isTodo,
                                 @NonNull Integer colorCode, String deadline) {
        this.id = id;
        this.content = content;
        this.isTodo = isTodo;
        this.colorCode = colorCode;
        this.deadline = deadline;
    }

    public ClassSideNoteResponse(Integer errorFlag) {
        this.errorFlag = errorFlag;
    }

//    private LocalDateTime createdAt;
//
//    private LocalDateTime updatedAt;
}