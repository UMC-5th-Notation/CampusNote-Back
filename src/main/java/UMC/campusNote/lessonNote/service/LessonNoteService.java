package UMC.campusNote.lessonNote.service;

import UMC.campusNote.common.exception.GeneralException;
import UMC.campusNote.common.s3.S3Provider;
import UMC.campusNote.common.s3.dto.S3UploadRequest;
import UMC.campusNote.lessonNote.converter.LessonNoteConverter;
import UMC.campusNote.lessonNote.entity.LessonNote;
import UMC.campusNote.lessonNote.repository.LessonNoteRepository;
import UMC.campusNote.note.entity.Note;
import UMC.campusNote.note.repository.NoteRepository;
import UMC.campusNote.user.entity.User;
import UMC.campusNote.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static UMC.campusNote.common.code.status.ErrorStatus.LESSONNOTE_NOT_FOUND;
import static UMC.campusNote.common.code.status.ErrorStatus.NOTE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class LessonNoteService {
    private final LessonNoteRepository lessonNoteRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final S3Provider s3Provider;

    public LessonNote createLessonNote(Long noteId, MultipartFile file, Long userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException());
        Note note = noteRepository.findById(noteId).orElseThrow(()->  new GeneralException(NOTE_NOT_FOUND));


        String fileDir = "lessonNotes/" + noteId;

        S3UploadRequest s3UploadRequest = new S3UploadRequest(user.getId(), fileDir);
        String imgUrl = s3Provider.fileUpload(file, s3UploadRequest);

        LessonNote existLessonNote = lessonNoteRepository.findByNoteId(noteId);
        if (existLessonNote != null){ // 이미 존재
            // 강노 업데이트
            existLessonNote.setLessonNote(imgUrl);
            return lessonNoteRepository.save(existLessonNote);
        } else{
            // 새 강노 생성
            LessonNote newLessonNote = LessonNoteConverter.toLessonNote(note);
            newLessonNote.setLessonNote(imgUrl);
            return lessonNoteRepository.save(newLessonNote);
        }
    }

    public LessonNote getLessonNoteByNoteId(Long noteId) throws GeneralException {
        Note note = noteRepository.findById(noteId).orElseThrow(()-> new GeneralException(NOTE_NOT_FOUND));
        LessonNote lessonNote = lessonNoteRepository.findByNoteId(noteId);
        if (lessonNote == null) {
            throw new GeneralException(LESSONNOTE_NOT_FOUND);
        } else {
            return lessonNote;
        }
    }

    public LessonNote deleteLessonNoteByNoteId(Long noteId) throws GeneralException {
        Note note = noteRepository.findById(noteId).orElseThrow(()-> new GeneralException(NOTE_NOT_FOUND));
        LessonNote lessonNote = lessonNoteRepository.findByNoteId(noteId);
        if (lessonNote == null) {
            throw new GeneralException(LESSONNOTE_NOT_FOUND);
        } else {
            lessonNoteRepository.delete(lessonNote);
            return lessonNote;
        }
    }
}
