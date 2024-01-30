package UMC.campusNote.classSideNote.service;

import static UMC.campusNote.classSideNote.status.ClassSideNoteErrorStatus.CLASS_SIDE_NOTE_BAD_COLOR_CODE;
import static UMC.campusNote.classSideNote.status.ClassSideNoteErrorStatus.CLASS_SIDE_NOTE_BAD_DEADLINE;
import static UMC.campusNote.classSideNote.status.ClassSideNoteErrorStatus.CLASS_SIDE_NOTE_BAD_REQUEST;
import static UMC.campusNote.classSideNote.status.ClassSideNoteErrorStatus.CLASS_SIDE_NOTE_NOT_FOUND;

import UMC.campusNote.classSideNote.dto.ClassSideNoteRequest;
import UMC.campusNote.classSideNote.entity.ClassSideNote;
import UMC.campusNote.classSideNote.exception.ClassSideNoteException;
import UMC.campusNote.classSideNote.repository.ClassSideNoteRepository;
import UMC.campusNote.mapping.UserLesson.UserLessonRepository;
import UMC.campusNote.mapping.UserLesson.UserLesson;


import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class ClassSideNoteService {

    ClassSideNoteRepository classSideNoteRepository;
    UserLessonRepository userLessonRepository;

    public ClassSideNote createClassSideNote(Long userLessonId, ClassSideNoteRequest request) {
        validateUserLesson(userLessonId);
        UserLesson userLesson = userLessonRepository.findById(userLessonId).orElseThrow();
        // 임시로 elseThrow
        validateRequest(request);

        ClassSideNote classSideNote = ClassSideNote.builder().
                userLesson(userLesson).
                content(request.getContent()).
                isTodo(request.getIsTodo()).
                colorCode(request.getColorCode()).
                deadline(request.getDeadline()).
                build();

        classSideNoteRepository.save(classSideNote);
        return classSideNote;
    }

    public ClassSideNote updateClassSideNote(Long id, ClassSideNoteRequest request) {
        validateClassSideNoteId(id);
        ClassSideNote oldClassSideNote = classSideNoteRepository.findById(id).orElseThrow();
        // 임시로 elseThrow

        ClassSideNote classSideNote = oldClassSideNote.update(request);
        validateClassSideNoteId(classSideNote.getId());

        return classSideNote;
    }

    public ClassSideNote getClassSideNoteById(Long id){
        validateClassSideNoteId(id);

        ClassSideNote classSideNote = classSideNoteRepository.findById(id).orElseThrow();

        // Todo : 검증로직 추가

        return classSideNote;
    }

    public List<ClassSideNote> getClassSideNoteListByUserLessonId(Long userLessonId) {
        validateUserLesson(userLessonId);
        UserLesson userLesson = userLessonRepository.findById(userLessonId).orElseThrow();
        // 임시로 elseThrow

        List<ClassSideNote> classSideNoteList = classSideNoteRepository.findByUserLesson(userLesson);

        //sort(classSideNoteList); // 생성/수정 기준 최근순
        classSideNoteList.sort(Comparator.comparing(ClassSideNote::getCreatedAt).reversed());
        // 생성 기준 최근순

        classSideNoteList.stream()
                .map(ClassSideNote::getId)
                .forEach(this::validateClassSideNoteId);

        return classSideNoteList;
    }



    public Slice<ClassSideNote> getClassSideNotesByUserLessonId(Long userLessonId, int page, int size) {
        validateUserLesson(userLessonId);
        UserLesson userLesson = userLessonRepository.findById(userLessonId).orElseThrow();

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Slice<ClassSideNote> classSideNoteSlice = classSideNoteRepository.findSliceBy(userLesson, pageable);

        classSideNoteSlice.forEach(classSideNote -> validateClassSideNoteId(classSideNote.getId()));

        return classSideNoteSlice;
    }


    public void deleteById(Long id){
        validateClassSideNoteId(id);

        classSideNoteRepository.deleteById(id);
    }



    private void validateClassSideNoteId(Long id) {
        if (!classSideNoteRepository.existsById(id)) {
            throw new ClassSideNoteException(CLASS_SIDE_NOTE_NOT_FOUND);
        }
    }

    private void validateUserLesson(Long userLessonId) {
        if (!userLessonRepository.existsById(userLessonId)) {
            throw new ClassSideNoteException(CLASS_SIDE_NOTE_BAD_REQUEST);
        }
    }


    private void validateRequest(ClassSideNoteRequest request) {
        if (request == null) {
            throw new ClassSideNoteException(CLASS_SIDE_NOTE_BAD_REQUEST);
        }
        if (request.getIsTodo() && request.getDeadline() == null) {
            throw new ClassSideNoteException(CLASS_SIDE_NOTE_BAD_DEADLINE);
        }
        if (request.getColorCode() < 0x000000 || request.getColorCode() > 0xFFFFFF) {
            throw new ClassSideNoteException(CLASS_SIDE_NOTE_BAD_COLOR_CODE);
        }
    }
}
