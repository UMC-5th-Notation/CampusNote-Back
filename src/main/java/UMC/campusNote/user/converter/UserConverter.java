package UMC.campusNote.user.converter;

import UMC.campusNote.user.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

    public static UserResponseDTO.AttendedSemesterUpdateDTO toAttendedSemesterUpdateDTO(String attendedSemester, int count) {
        return UserResponseDTO.AttendedSemesterUpdateDTO.builder()
                .count(count)
                .attendedSemester(attendedSemester)
                .build();
    }
}
