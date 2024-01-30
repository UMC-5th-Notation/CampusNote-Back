package UMC.campusNote.classSideNote.controller;

import UMC.campusNote.classSideNote.dto.ClassSideNoteRequest;
import UMC.campusNote.classSideNote.dto.ClassSideNoteResponse;
import UMC.campusNote.classSideNote.entity.ClassSideNote;
import UMC.campusNote.classSideNote.exception.ClassSideNoteException;
import UMC.campusNote.classSideNote.service.ClassSideNoteService;

import UMC.campusNote.common.ApiResponse;


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
@RequestMapping("/api/class-side-note")
@AllArgsConstructor
public class ClassSideNoteController {

    ClassSideNoteService classSideNoteService;

    @PostMapping("/create/")
    public ApiResponse<ClassSideNoteResponse> createClassSideNote(@RequestParam Long userLessonId,
                                                                  @RequestBody ClassSideNoteRequest request) {
        // Todo: Error 처리 로직 추가하기

        //try {

        ClassSideNote classSideNote = classSideNoteService.createClassSideNote(userLessonId, request);

        ClassSideNoteResponse response = convert(classSideNote);

        return ApiResponse.onSuccess(response);

        //} catch (Exception e) {
        //}
    }


    @GetMapping("/{id}")
    public ApiResponse<ClassSideNoteResponse> getClassSideNoteById(@PathVariable Long id) {
        // Todo: Error 처리 로직 추가하기
//        try {
        ClassSideNote classSideNote = classSideNoteService.getClassSideNoteById(id);

        ClassSideNoteResponse response = convert(classSideNote);

        return ApiResponse.onSuccess(response);

//        } catch (ClassSideNoteException e) {
//        }
    }

    @PutMapping("/{id}")
    public ApiResponse<ClassSideNoteResponse> updateClassSideNote(
            @PathVariable Long id, @RequestBody ClassSideNoteRequest request) {
        // Todo: Error 처리 로직 추가하기
        //try {
        ClassSideNote classSideNote = classSideNoteService.updateClassSideNote(id, request);

        ClassSideNoteResponse response = convert(classSideNote);

        return ApiResponse.onSuccess(response);
        //} catch (Exception e) {
        //}
    }

    @PatchMapping("/{id}")
    public ApiResponse<ClassSideNoteResponse> updateClassSideNoteContent(
            @PathVariable Long id, @RequestParam String content) {
        // Todo: Error 처리 로직 추가하기
        //try {
        ClassSideNote classSideNote = classSideNoteService.getClassSideNoteById(id);
        classSideNote.updateContent(content);

        ClassSideNoteResponse response = convert(classSideNote);

        return ApiResponse.onSuccess(response);
        //} catch (Exception e) {
        //}
    }

    @DeleteMapping("/{id}")
    public ApiResponse<ClassSideNoteResponse> deleteClassSideNoteById(@PathVariable Long id) {
        // Todo: Error 처리 로직 추가하기
        //try {
        ClassSideNote classSideNote = classSideNoteService.getClassSideNoteById(id);

        classSideNoteService.deleteById(id);

        ClassSideNoteResponse response = convert(classSideNote);

        return ApiResponse.onSuccess(response); // 삭제된 내용 반환

        //} catch (Exception e) {
        //}
    }


    private ClassSideNoteResponse convert(ClassSideNote classSideNote) {
        return ClassSideNoteResponse.builder()
                .id(classSideNote.getId())
                .content(classSideNote.getContent())
                .isTodo(classSideNote.getIsTodo())
                .colorCode(classSideNote.getColorCode())
                .deadline(classSideNote.getDeadline() != null ? classSideNote.getDeadline().toString() : null)
                .build();
    }
}
