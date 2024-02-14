package UMC.campusNote.page.service;

import UMC.campusNote.common.s3.S3Provider;
import UMC.campusNote.common.s3.dto.S3UploadRequest;
import UMC.campusNote.note.entity.Note;
import UMC.campusNote.note.repository.NoteRepository;
import UMC.campusNote.page.converter.PageConverter;
import UMC.campusNote.page.dto.PageResponseDTO;
import UMC.campusNote.page.repository.PageRepository;
import UMC.campusNote.page.dto.PageRequestDTO;
import UMC.campusNote.page.entity.Page;
import UMC.campusNote.user.entity.User;
import UMC.campusNote.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PageService {
   private final PageRepository pageRepository;
   private final NoteRepository noteRepository;
   private final UserRepository userRepository;
   private final S3Provider s3Provider;

   public Page writePage(PageRequestDTO.PageDTO request, MultipartFile file, Long userId) throws IOException {
      User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException());
      Note note = noteRepository.findById(request.getNoteId()).orElseThrow(()-> new RuntimeException());

      String fileDir = "pages/" + request.getNoteId() + request.getPageNum();

      S3UploadRequest s3UploadRequest = new S3UploadRequest(user.getId(), fileDir);
      String imgUrl = s3Provider.fileUpload(file, s3UploadRequest);

      // 이미 존재하는 페이지인지 확인
      Page existingPage = pageRepository.findByNoteIdAndPageNumber(request.getNoteId(), request.getPageNum());
      if (existingPage != null) { // 이미 존재
         //페이지 업데이트
         existingPage.setRound(request.getRound());
         existingPage.setSideNote(request.getSideNote());
         existingPage.setHandWritingSVG(imgUrl);
         return pageRepository.save(existingPage);
      } else{
         // 새 페이지 생성
         Page newPage = PageConverter.toPage(request, note);
         newPage.setHandWritingSVG(imgUrl);
         return pageRepository.save(newPage);
      }
   }

   public List<PageResponseDTO.GetPageResultDTO> getPagesOfNote(Long noteId) throws IOException{
      List<Page> pageList = pageRepository.findAllByNoteId(noteId);
      List<PageResponseDTO.GetPageResultDTO> getPageRes = pageList.stream()
              .map(pages -> new PageResponseDTO.GetPageResultDTO(pages.getId(), pages.getHandWritingSVG(), pages.getPageNumber(), pages.getSideNote()))
              .sorted(Comparator.comparingInt(PageResponseDTO.GetPageResultDTO::getPageNumber)) // 페이지 번호로 정렬
              .collect(Collectors.toList());
      return getPageRes;
   }

}
