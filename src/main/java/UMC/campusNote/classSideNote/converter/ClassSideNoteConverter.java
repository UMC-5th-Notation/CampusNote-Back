package UMC.campusNote.classSideNote.converter;

import UMC.campusNote.classSideNote.dto.ClassSideNoteResponseDTO.ClassSideNoteResultDTO;
import UMC.campusNote.classSideNote.entity.ClassSideNote;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClassSideNoteConverter {
    public ClassSideNoteResultDTO toClassSideNoteResultDTO(ClassSideNote classSideNote) {
        return ClassSideNoteResultDTO.builder()
                .id(classSideNote.getId())
                .content(classSideNote.getContent())
                .isTodo(classSideNote.getIsTodo())
                .colorCode(classSideNote.getColorCode())
                .deadline(classSideNote.getDeadline() != null ?
                        classSideNote.getDeadline().toString() : null)
                .build();
    }

    public List<ClassSideNoteResultDTO> toClassSideNoteResultDTOList(List<ClassSideNote> classSideNoteList) {
        return classSideNoteList.stream()
                .map(this::toClassSideNoteResultDTO)
                .collect(Collectors.toList());
    }
}
