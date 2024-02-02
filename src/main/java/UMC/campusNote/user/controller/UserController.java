package UMC.campusNote.user.controller;

import UMC.campusNote.common.ApiResponse;
import UMC.campusNote.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ApiResponse<String> get() {
        return ApiResponse.onSuccess("GET:: user controller");
    }

}
