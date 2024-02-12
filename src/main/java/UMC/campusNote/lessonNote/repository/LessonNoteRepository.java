package UMC.campusNote.lessonNote.repository;

import UMC.campusNote.lessonNote.entity.LessonNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonNoteRepository extends JpaRepository<LessonNote, Long> {
    LessonNote findByNoteId(Long noteId);
}
