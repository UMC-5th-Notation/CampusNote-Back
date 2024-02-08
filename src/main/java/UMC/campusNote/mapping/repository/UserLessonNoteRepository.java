package UMC.campusNote.mapping.repository;

import UMC.campusNote.mapping.UserLessonNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLessonNoteRepository extends JpaRepository<UserLessonNote, Long> {
}
