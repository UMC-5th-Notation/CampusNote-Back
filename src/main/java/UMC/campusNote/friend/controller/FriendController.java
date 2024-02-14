package UMC.campusNote.friend.controller;

import UMC.campusNote.common.ApiResponse;

import UMC.campusNote.friend.dto.FriendRequestDTO;
import UMC.campusNote.friend.service.FriendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friend")
public class FriendController {

    private final FriendService friendService;
    @PostMapping
    private ApiResponse addFriend(@Valid @RequestBody FriendRequestDTO.AddFriendReqDTO addFriendReqDto){

        friendService.addFriend(addFriendReqDto);

        return ApiResponse.onSuccess(null);
    }
}
