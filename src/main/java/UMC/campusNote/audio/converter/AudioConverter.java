package UMC.campusNote.audio.converter;

import UMC.campusNote.audio.dto.AudioResponseDTO;

public class AudioConverter {

    public static AudioResponseDTO.AudioDTO toAudioDTO(Long audioId, String audioFile) {
        return AudioResponseDTO.AudioDTO.builder()
                .audioId(audioId)
                .audioFile(audioFile)
                .build();
    }
}
