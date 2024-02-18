package UMC.campusNote.friend.controller;

import UMC.campusNote.auth.jwt.JwtProvider;
import UMC.campusNote.common.ApiResponse;

import UMC.campusNote.friend.dto.FriendRequestDTO;
import UMC.campusNote.friend.dto.FriendResponseDTO;
import UMC.campusNote.friend.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
public class FriendController {

    private final FriendService friendService;

    @PostMapping
    @Operation(summary = "친구 추가 기능 API", description = "친구 관계를 추가하는 API입니다.")
    private ApiResponse addFriend(@Valid @RequestBody FriendRequestDTO.AddFriendReqDTO addFriendReqDto){

        friendService.addFriend(addFriendReqDto);

        return ApiResponse.onSuccess(null);
    }


    @GetMapping("/{userId}")
    @Operation(summary = "친구 조회 기능 API", description = "유저의 친구를 조회하는 API입니다.")
    @Parameters({
            @Parameter(name = "userId", description = "유저 id 입니다.")
    })
    private ApiResponse<List<FriendResponseDTO.friendListDTO>> friendList(@PathVariable("userId") Long userId){
        List<FriendResponseDTO.friendListDTO> friendList = friendService.findFriendList(userId);

        return ApiResponse.onSuccess(friendList);
    }

    @DeleteMapping("/{userId}/{friendId}")
    @Operation(summary = "친구 삭제 기능 API", description = "")
    private ApiResponse deleteFriend(@PathVariable("userId") Long userId, @PathVariable("friendId") Long friendId){
        friendService.deleteFriend(userId, friendId);

        return ApiResponse.onSuccess(null);
    }
}
