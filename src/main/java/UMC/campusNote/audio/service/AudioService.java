package UMC.campusNote.audio.service;

import UMC.campusNote.audio.dto.AudioResDto;
import UMC.campusNote.common.s3.dto.S3UploadRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface AudioService {
    AudioResDto getAudio(Long audioId);

    Slice<AudioResDto> getAudios(Long noteId, Pageable pageable);
    AudioResDto saveAudio(S3UploadRequest request, Long noteId, MultipartFile audioFile);


    AudioResDto deleteAudio(Long audioId);
}
