package UMC.campusNote.page.converter;

import UMC.campusNote.note.entity.Note;
import UMC.campusNote.page.dto.PageRequestDTO;
import UMC.campusNote.page.dto.PageResponseDTO;
import UMC.campusNote.page.entity.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageConverter {
    public static PageResponseDTO.PageResultDTO toPageResultDTO(Page page){
        return PageResponseDTO.PageResultDTO.builder()
                .pageId(page.getId())
                .noteId(page.getNote().getId())
                .build();
    }

    public static Page toPage(PageRequestDTO.PageDTO request, Note note){
        return Page.builder()
                .note(note)
                .pageNumber(request.getPageNum())
                .sideNote(request.getSideNote())
                .round(request.getRound())
                .build();
    }


}
