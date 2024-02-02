package UMC.campusNote.classSideNote.repository;

import UMC.campusNote.classSideNote.entity.ClassSideNote;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassSideNoteRepository extends JpaRepository<ClassSideNote, Long> {
    List<ClassSideNote> findByUserLessonId(Long userLessonId);
}
