package UMC.campusNote.classSideNote.dto;

import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


public class ClassSideNoteResponseDTO {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class ClassSideNoteResponse {
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


}