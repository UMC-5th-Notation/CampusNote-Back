package UMC.campusNote.audio.service;

import UMC.campusNote.audio.dto.AudioResponseDTO;
import UMC.campusNote.common.s3.dto.S3UploadRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public interface AudioService {
    AudioResponseDTO.AudioDTO getAudio(Long audioId);
    Slice<AudioResponseDTO.AudioDTO> getAudios(Long noteId, Pageable pageable);
    AudioResponseDTO.AudioDTO saveAudio(S3UploadRequest request, Long noteId, MultipartFile audioFile);
    AudioResponseDTO.AudioDTO deleteAudio(Long audioId);
}
