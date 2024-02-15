package UMC.campusNote.user.service;


import UMC.campusNote.mapping.repository.UserLessonRepository;
import UMC.campusNote.user.converter.UserConverter;
import UMC.campusNote.user.dto.UserRequestDTO;
import UMC.campusNote.user.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserLessonRepository userLessonRepository;

    @Override
    @Transactional
    public UserResponseDTO.AttendedSemesterUpdateDTO updateAttendedSemester(Long userId, UserRequestDTO.AttendedSemesterUpdateDTO request) {
        int count = userLessonRepository.updateAttendedSemester(userId, request.getAttendedSemester());
        return UserConverter.toAttendedSemesterUpdateDTO(request.getAttendedSemester(), count);
    }
}
