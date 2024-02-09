package UMC.campusNote.classSideNote.controller;

import UMC.campusNote.classSideNote.converter.ClassSideNoteConverter;
import UMC.campusNote.classSideNote.dto.ClassSideNoteRequestDTO.CreateSideNoteRequest;
import UMC.campusNote.classSideNote.dto.ClassSideNoteRequestDTO.UpdateSideNoteRequest;
import UMC.campusNote.classSideNote.dto.ClassSideNoteResponseDTO.ClassSideNoteResponse;
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
    public ApiResponse<ClassSideNoteResponse> createClassSideNote(
            @RequestParam Long userLessonId,
            @RequestBody CreateSideNoteRequest request) {
        ClassSideNoteResponse response = classSideNoteService.createClassSideNote(userLessonId, request);

        return ApiResponse.onSuccess(response);
    }


    @GetMapping("/{classSideNoteId}")
    public ApiResponse<ClassSideNoteResponse> getClassSideNoteById(
            @PathVariable Long classSideNoteId) {
        ClassSideNoteResponse response = classSideNoteService.getClassSideNoteById(classSideNoteId);

        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/{userLessonId}/list")
    public ApiResponse<Collection<ClassSideNoteResponse>> getClassSideNoteListByUserLessonId(
            @PathVariable("userLessonId") Long userLessonId
    ) {
        List<ClassSideNote> classSideNoteList =
                classSideNoteService.getClassSideNoteListByUserLessonId(userLessonId);

        List<ClassSideNoteResponse> classSideNoteResponseList = classSideNoteConverter.convertList(classSideNoteList);
        return ApiResponse.onSuccess(classSideNoteResponseList);
    }

    @PutMapping("/{classSideNoteId}")
    public ApiResponse<ClassSideNoteResponse> updateClassSideNote(
            @PathVariable Long classSideNoteId,
            @RequestBody UpdateSideNoteRequest request) {
        ClassSideNoteResponse response = classSideNoteService.updateClassSideNote(classSideNoteId, request);

        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/{classSideNoteId}")
    public ApiResponse<ClassSideNoteResponse> updateClassSideNoteContent(
            @PathVariable Long classSideNoteId,
            @RequestParam String content) {
        ClassSideNoteResponse response = classSideNoteService.updateClassSideNoteContent(classSideNoteId, content);

        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/{classSideNoteId}")
    public ApiResponse<ClassSideNoteResponse> deleteClassSideNoteById(
            @PathVariable
            Long classSideNoteId) {
        ClassSideNoteResponse response = classSideNoteService.getClassSideNoteById(classSideNoteId);

        classSideNoteService.deleteById(classSideNoteId);

        return ApiResponse.onSuccess(response); // 삭제된 내용 반환
    }


}
