package UMC.campusNote.audio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AudioResponseDTO {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class AudioDTO {
        private Long audioId;
        private String audioFile;
    }
}
