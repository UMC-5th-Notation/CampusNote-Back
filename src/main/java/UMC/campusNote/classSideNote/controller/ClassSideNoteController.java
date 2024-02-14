package UMC.campusNote.classSideNote.controller;

import UMC.campusNote.classSideNote.converter.ClassSideNoteConverter;
import UMC.campusNote.classSideNote.dto.ClassSideNoteRequestDTO.ClassSideNoteCreateDTO;
import UMC.campusNote.classSideNote.dto.ClassSideNoteRequestDTO.ClassSideNoteUpdateDTO;
import UMC.campusNote.classSideNote.dto.ClassSideNoteResponseDTO.ClassSideNoteResultDTO;
import UMC.campusNote.classSideNote.entity.ClassSideNote;
import UMC.campusNote.classSideNote.service.ClassSideNoteService;

import UMC.campusNote.common.ApiResponse;

import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/class-side-note")
@AllArgsConstructor
public class ClassSideNoteController {

    ClassSideNoteService classSideNoteService;
    ClassSideNoteConverter classSideNoteConverter;

    @PostMapping("/create/")
    public ApiResponse<ClassSideNoteResultDTO> createClassSideNote(
            @RequestParam Long userLessonId,
            @RequestBody ClassSideNoteCreateDTO request) {
        ClassSideNoteResultDTO response = classSideNoteService.createClassSideNote(userLessonId, request);

        return ApiResponse.onSuccess(response);
    }


    @GetMapping("/{classSideNoteId}")
    public ApiResponse<ClassSideNoteResultDTO> getClassSideNoteById(
            @PathVariable Long classSideNoteId) {
        ClassSideNoteResultDTO response = classSideNoteService.getClassSideNoteById(classSideNoteId);

        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/{userLessonId}/list")
    public ApiResponse<Collection<ClassSideNoteResultDTO>> getClassSideNoteListByUserLessonId(
            @PathVariable("userLessonId") Long userLessonId
    ) {
        List<ClassSideNote> classSideNoteList =
                classSideNoteService.getClassSideNoteListByUserLessonId(userLessonId);

        List<ClassSideNoteResultDTO> classSideNoteResultDTOList = classSideNoteConverter.toClassSideNoteResultDTOList(classSideNoteList);
        return ApiResponse.onSuccess(classSideNoteResultDTOList);
    }

    @PutMapping("/{classSideNoteId}")
    public ApiResponse<ClassSideNoteResultDTO> updateClassSideNote(
            @PathVariable Long classSideNoteId,
            @RequestBody ClassSideNoteUpdateDTO request) {
        ClassSideNoteResultDTO response = classSideNoteService.updateClassSideNote(classSideNoteId, request);

        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/{classSideNoteId}")
    public ApiResponse<ClassSideNoteResultDTO> updateClassSideNoteContent(
            @PathVariable Long classSideNoteId,
            @RequestParam String content) {
        ClassSideNoteResultDTO response = classSideNoteService.updateClassSideNoteContent(classSideNoteId, content);

        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/{classSideNoteId}")
    public ApiResponse<ClassSideNoteResultDTO> deleteClassSideNoteById(
            @PathVariable
            Long classSideNoteId) {
        ClassSideNoteResultDTO response = classSideNoteService.getClassSideNoteById(classSideNoteId);

        classSideNoteService.deleteById(classSideNoteId);

        return ApiResponse.onSuccess(response); // 삭제된 내용 반환
    }


}
