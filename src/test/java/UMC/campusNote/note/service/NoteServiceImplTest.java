package UMC.campusNote.note.service;

import UMC.campusNote.lesson.entity.Lesson;
import UMC.campusNote.lesson.repository.LessonRepository;
import UMC.campusNote.lesson.service.LessonService;
import UMC.campusNote.mapping.UserLesson;
import UMC.campusNote.mapping.repository.UserLessonNoteRepository;
import UMC.campusNote.mapping.repository.UserLessonRepository;
import UMC.campusNote.note.dto.NoteRequestDTO;
import UMC.campusNote.note.dto.NoteResponseDTO;
import UMC.campusNote.note.repository.NoteRepository;
import UMC.campusNote.user.entity.Role;
import UMC.campusNote.user.entity.User;
import UMC.campusNote.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class NoteServiceImplTest {

    @Autowired
    NoteServiceImpl noteService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserLessonRepository userLessonRepository;
    @Autowired
    LessonRepository lessonRepository;
    @Autowired
    EntityManager em;
    @Autowired
    NoteRepository noteRepository;

    @BeforeEach
    @Transactional
    void setUp() {
        noteRepository.deleteAll();
        userLessonRepository.deleteAll();
        lessonRepository.deleteAll();
        userRepository.deleteAll();

        User user = User.builder()
                .role(Role.USER)
                .clientId("test")
                .build();
        Lesson lesson = Lesson.builder()
                .lessonName("객체지향프로그래밍 2")
                .semester("2023년 2학기")
                .build();

        UserLesson userLesson = UserLesson.builder()
                .user(user)
                .lesson(lesson)
                .attendedSemester("2023년 2학기")
                .build();

        userRepository.save(user);
        lessonRepository.save(lesson);
        userLessonRepository.save(userLesson);
    }

    @Test
    @Transactional
    @DisplayName("[특정 학기 특정 유저레슨의 노트 생성]")
    void createUserNote() {
        User user = userRepository.findByClientId("test").get();
        Lesson lesson = lessonRepository.findByLessonName("객체지향프로그래밍 2").get();
        UserLesson findUsesrLesson = userLessonRepository.findByUserAndAndAttendedSemesterAndAndLesson(user, "2023년 2학기", lesson).get();
        NoteRequestDTO.NoteCreateDTO request = new NoteRequestDTO.NoteCreateDTO(findUsesrLesson.getId(), "2023년 2학기", "노트제목");

        // when
        NoteResponseDTO.NoteCreateDTO noteCreateDTO = noteService.createUserNote(user, request);
        // then
        noteRepository.findById(noteCreateDTO.getNoteId()).ifPresent(note -> {
            assert note.getNoteName().equals("노트제목");
        });
    }

}