package UMC.campusNote.image.converter;

import UMC.campusNote.image.dto.ImageResponseDTO;
import UMC.campusNote.image.entity.Image;
import UMC.campusNote.note.entity.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageConverter {
    public static ImageResponseDTO.CreateImageResultDTO toImageResultDTO(Image image){
        return ImageResponseDTO.CreateImageResultDTO.builder()
                .imageId(image.getId())
                .noteId(image.getNote().getId())
                .fileUrl(image.getImg())
                .build();

    }

    public static ImageResponseDTO.GetImageResultDTO toGetImageResultDTO(Image image){
        return ImageResponseDTO.GetImageResultDTO.builder()
                .imageId(image.getId())
                .imgUrl(image.getImg())
                .build();
    }

    public static Image toImage(Note note){
        return Image.builder()
                .note(note)
                .build();
    }
}
