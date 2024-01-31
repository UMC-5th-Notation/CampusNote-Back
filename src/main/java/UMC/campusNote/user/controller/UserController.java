package UMC.campusNote.user.controller;

import UMC.campusNote.common.ApiResponse;

import UMC.campusNote.user.dto.JoinReqDto;
import UMC.campusNote.user.dto.JoinResDto;
import UMC.campusNote.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signUp")
    public ApiResponse<JoinResDto> join(@RequestBody @Valid JoinReqDto joinReqDto){

        JoinResDto res = userService.join(joinReqDto);

        return ApiResponse.onSuccess(res);
    }

}
