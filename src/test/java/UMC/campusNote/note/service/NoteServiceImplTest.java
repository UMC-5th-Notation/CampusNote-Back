package UMC.campusNote.note.service;

import UMC.campusNote.lesson.entity.Lesson;
import UMC.campusNote.lesson.repository.LessonRepository;
import UMC.campusNote.mapping.UserLesson;
import UMC.campusNote.mapping.repository.UserLessonRepository;
import UMC.campusNote.note.dto.NoteRequestDTO;
import UMC.campusNote.note.dto.NoteResponseDTO;
import UMC.campusNote.note.entity.Note;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@ActiveProfiles("test")
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
    @DisplayName("[특정 학기 특정 유저레슨의 특정 노트 단일 조회]")
    void getAllUserNote() {
        User user = userRepository.findByClientId("test").get();
        Lesson lesson = lessonRepository.findByLessonName("객체지향프로그래밍 2").get();
        UserLesson findUsesrLesson = userLessonRepository.findByUserAndAndAttendedSemesterAndAndLesson(user, "2023년 2학기", lesson).get();
        NoteRequestDTO.NoteCreateDTO request = new NoteRequestDTO.NoteCreateDTO("2023년 2학기", "노트제목");
        NoteResponseDTO.NoteCreateDTO noteCreateDTO = noteService.createUserNote(user, findUsesrLesson.getId(), request);
        // when
        Note note = noteRepository.findByNoteName("노트제목").get();

        // then
        assert note.getId().equals(noteCreateDTO.getNoteId());
    }

    @Test
    @Transactional
    @DisplayName("[특정 학기 특정 유저레슨의 노트 전체 조회]")
    void getAllUserNotes() {
        User user = userRepository.findByClientId("test").get();
        Lesson lesson = lessonRepository.findByLessonName("객체지향프로그래밍 2").get();
        UserLesson findUsesrLesson = userLessonRepository.findByUserAndAndAttendedSemesterAndAndLesson(user, "2023년 2학기", lesson).get();
        NoteRequestDTO.NoteCreateDTO request2 = new NoteRequestDTO.NoteCreateDTO("2023년 2학기", "노트제목");
        noteService.createUserNote(user, findUsesrLesson.getId(), request2);
        Slice<NoteResponseDTO.NoteGetDTO> userNotes = noteService.getUserNotes(user, findUsesrLesson.getId(), "2023년 2학기", Pageable.ofSize(10));
        // then
        assert userNotes.getContent().size() == 1;
    }

    @Test
    @Transactional
    @DisplayName("[특정 학기 특정 유저레슨의 노트 생성]")
    void createUserNote() {
        User user = userRepository.findByClientId("test").get();
        Lesson lesson = lessonRepository.findByLessonName("객체지향프로그래밍 2").get();
        UserLesson findUsesrLesson = userLessonRepository.findByUserAndAndAttendedSemesterAndAndLesson(user, "2023년 2학기", lesson).get();
        NoteRequestDTO.NoteCreateDTO request = new NoteRequestDTO.NoteCreateDTO("2023년 2학기", "노트제목");

        // when
        NoteResponseDTO.NoteCreateDTO noteCreateDTO = noteService.createUserNote(user, findUsesrLesson.getId(), request);
        // then
        noteRepository.findById(noteCreateDTO.getNoteId()).ifPresent(note -> {
            assert note.getNoteName().equals("노트제목");
        });
    }

    @Test
    @Transactional
    @DisplayName("[특정 학기 특정 유저레슨의 특정 노트 수정]")
    void updateNoteName() {
        User user = userRepository.findByClientId("test").get();
        Lesson lesson = lessonRepository.findByLessonName("객체지향프로그래밍 2").get();
        UserLesson findUsesrLesson = userLessonRepository.findByUserAndAndAttendedSemesterAndAndLesson(user, "2023년 2학기", lesson).get();
        NoteRequestDTO.NoteCreateDTO request = new NoteRequestDTO.NoteCreateDTO("2023년 2학기", "노트제목");
        NoteResponseDTO.NoteCreateDTO noteCreateDTO = noteService.createUserNote(user, findUsesrLesson.getId(), request);
        Note note = noteRepository.findByNoteName("노트제목").get();
        // when
        NoteResponseDTO.NoteUpdateDTO noteUpdateDTO = noteService.updateUserNote(user, note.getId(), new NoteRequestDTO.NoteUpdateDTO("수정된노트제목"));
        em.flush();
        em.clear();

        // then
        assert noteUpdateDTO.getNoteId().equals(note.getId());
        assert note.getNoteName().equals("수정된노트제목");

    }

}