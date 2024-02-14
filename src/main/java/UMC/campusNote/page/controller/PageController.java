package UMC.campusNote.page.controller;

import UMC.campusNote.common.ApiResponse;
import UMC.campusNote.page.converter.PageConverter;
import UMC.campusNote.page.dto.PageRequestDTO;
import UMC.campusNote.page.dto.PageResponseDTO;
import UMC.campusNote.page.service.PageService;
import UMC.campusNote.page.entity.Page;
import UMC.campusNote.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pages")
public class PageController {
    private final PageService pageService;

    @Operation(summary = "페이지 필기 등록 API", description = "Header : jwt 토큰 " +
            "Body : request 파라미터로 noteId, pageNum, sideNote, round // image 파라미터로 파일 넣기" +
            "noteId, pageNum가 이미 존재하는 데이터면 업데이트(round, sideNote, image)")
    @PostMapping
    public ApiResponse<PageResponseDTO.PageResultDTO> write(@AuthenticationPrincipal User user, @RequestPart("request") PageRequestDTO.PageDTO request, @RequestPart("image") MultipartFile image) throws IOException {
        Page newPage = pageService.writePage(request, image, user.getId());
        return ApiResponse.onSuccess(PageConverter.toPageResultDTO(newPage));
    }

    @Operation(summary = "노트아이디로 페이지 조회 API", description = "pathVariable로 noteId")
    @GetMapping("/{noteId}")
    public ApiResponse<List<PageResponseDTO.GetPageResultDTO>> getPages(@PathVariable Long noteId) throws IOException {
        return ApiResponse.onSuccess(pageService.getPagesOfNote(noteId));
    }

}
