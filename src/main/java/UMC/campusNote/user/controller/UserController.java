package UMC.campusNote.user.controller;

import UMC.campusNote.common.ApiResponse;
import UMC.campusNote.user.dto.UserRequestDTO;
import UMC.campusNote.user.dto.UserResponseDTO;
import UMC.campusNote.user.entity.User;
import UMC.campusNote.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static UMC.campusNote.common.code.status.SuccessStatus.ATTENDED_SEMESTER_UPDATE;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ApiResponse<String> get() {
        return ApiResponse.onSuccess("GET:: user controller");
    }

    @PatchMapping("/attended-semester")
    public ApiResponse<UserResponseDTO.AttendedSemesterUpdateDTO> updateAttendedSemester(@AuthenticationPrincipal User user, @RequestBody UserRequestDTO.AttendedSemesterUpdateDTO request) {
        return ApiResponse.of(ATTENDED_SEMESTER_UPDATE ,userService.updateAttendedSemester(user.getId(), request));
    }

}
