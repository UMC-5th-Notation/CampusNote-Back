package UMC.campusNote.image.service;

import UMC.campusNote.common.exception.GeneralException;
import UMC.campusNote.common.s3.S3Provider;
import UMC.campusNote.common.s3.dto.S3UploadRequest;
import UMC.campusNote.image.converter.ImageConverter;
import UMC.campusNote.image.dto.ImageResponseDTO;
import UMC.campusNote.image.entity.Image;
import UMC.campusNote.image.repository.ImageRepository;
import UMC.campusNote.note.entity.Note;
import UMC.campusNote.note.repository.NoteRepository;
import UMC.campusNote.user.entity.User;
import UMC.campusNote.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static UMC.campusNote.common.code.status.ErrorStatus.*;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final S3Provider s3Provider;
    private final ImageRepository imageRepository;

    public Image createImage(Long noteId, MultipartFile file, Long userId) throws IOException{
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException());
        Note note = noteRepository.findById(noteId).orElseThrow(()->  new GeneralException(NOTE_NOT_FOUND));

        String fileDir = "images/" + noteId;
        S3UploadRequest s3UploadRequest = new S3UploadRequest(user.getId(), fileDir);
        String imgUrl = s3Provider.fileUpload(file, s3UploadRequest);

        Image newImage = ImageConverter.toImage(note);
        newImage.setImg(imgUrl);
        return imageRepository.save(newImage);

    }

    public List<ImageResponseDTO.GetImageResultDTO> getImageOfNote(Long noteId) throws GeneralException{
        List<Image> imageList = imageRepository.findAllByNoteId(noteId);
        List<ImageResponseDTO.GetImageResultDTO> getImageRes = imageList.stream()
                .map(images -> new ImageResponseDTO.GetImageResultDTO(images.getId(), images.getImg()))
                .collect(Collectors.toList());
        if (getImageRes.size() < 1){
            throw new GeneralException(IMAGE_NOT_FOUND);
        }
        return getImageRes;
    }

    public ImageResponseDTO.GetImageResultDTO getOneImageOfNote(Long noteId, Long imageId) throws GeneralException{
        Note note = noteRepository.findById(noteId).orElseThrow(()-> new GeneralException(NOTE_NOT_FOUND));
        Image image = imageRepository.findByIdAndNoteId(imageId, noteId);
        if (image == null){
            throw new GeneralException(IMAGE_NOT_FOUND);
        }
        return ImageConverter.toGetImageResultDTO(image);
    }

    public List<ImageResponseDTO.GetImageResultDTO> deleteImageOfNote(Long noteId) throws GeneralException{
        Note note = noteRepository.findById(noteId).orElseThrow(()-> new GeneralException(NOTE_NOT_FOUND));
        List<Image> imageList = imageRepository.findAllByNoteId(noteId);
        if (imageList.size() < 1){
            throw new GeneralException(IMAGE_NOT_FOUND);
        }
        List<ImageResponseDTO.GetImageResultDTO> deleteImageRes = imageList.stream()
                .map(images -> new ImageResponseDTO.GetImageResultDTO(images.getId(), images.getImg()))
                .collect(Collectors.toList());

        imageRepository.deleteAllByNoteId(noteId);

        return deleteImageRes;

    }

    public ImageResponseDTO.GetImageResultDTO deleteOneImageOfNote(Long noteId, Long imageId) throws GeneralException{
        Note note = noteRepository.findById(noteId).orElseThrow(()-> new GeneralException(NOTE_NOT_FOUND));
        Image image = imageRepository.findByIdAndNoteId(imageId, noteId);
        if (image == null){
            throw new GeneralException(IMAGE_NOT_FOUND);
        }

        imageRepository.delete(image);
        return ImageConverter.toGetImageResultDTO(image);

    }

}
