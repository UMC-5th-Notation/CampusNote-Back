package UMC.campusNote.lessonNote.converter;

import UMC.campusNote.lessonNote.dto.LessonNoteResponseDTO;
import UMC.campusNote.lessonNote.entity.LessonNote;
import UMC.campusNote.note.entity.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LessonNoteConverter {
    public static LessonNoteResponseDTO.CreateResultDTO toLessonNoteResultDTO(LessonNote lessonNote){
        return LessonNoteResponseDTO.CreateResultDTO.builder()
                .lessonNoteId(lessonNote.getId())
                .noteId(lessonNote.getNote().getId())
                .fileUrl(lessonNote.getLessonNote())
                .build();
    }

    public static LessonNote toLessonNote(Note note){
        return LessonNote.builder()
                .note(note)
                .build();
    }

}
