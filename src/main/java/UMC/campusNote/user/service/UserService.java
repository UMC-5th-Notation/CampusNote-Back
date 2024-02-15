package UMC.campusNote.user.service;

import UMC.campusNote.user.dto.UserRequestDTO;
import UMC.campusNote.user.dto.UserResponseDTO;
import UMC.campusNote.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface UserService {

    UserResponseDTO.AttendedSemesterUpdateDTO updateAttendedSemester(Long userId, UserRequestDTO.AttendedSemesterUpdateDTO request);
}
