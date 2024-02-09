package UMC.campusNote.audio.dto;

import UMC.campusNote.audio.entity.Audio;
import UMC.campusNote.auth.dto.JoinResDto;
import UMC.campusNote.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AudioResDto {

    private Long audioId;
    private String audioFile;
    public static AudioResDto fromEntity(Long audioId, String audioFile){
        return AudioResDto.builder()
                .audioId(audioId)
                .audioFile(audioFile)
                .build();
    }
}
