package UMC.campusNote.classSideNote.repository;

import UMC.campusNote.classSideNote.entity.ClassSideNote;
import UMC.campusNote.mapping.UserLesson.UserLesson;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassSideNoteRepository extends JpaRepository<ClassSideNote, Long> {
    List<ClassSideNote> findByUserLesson(UserLesson userLesson);
    Slice<ClassSideNote> findSliceBy(UserLesson userLesson, Pageable pageable);
}
