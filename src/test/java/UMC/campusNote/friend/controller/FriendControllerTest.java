//package UMC.campusNote.friend.controller;
//
//import UMC.campusNote.friend.dto.AddFriendReqDto;
//import UMC.campusNote.friend.service.FriendService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(FriendControllerTest.class)
//public class FriendControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    FriendService friendService;
//
//    @Test
//    @DisplayName("AddFriendDto 유효성 테스트")
//    void validAddFriendDto() throws Exception{
//        AddFriendReqDto reqDto1 = AddFriendReqDto.builder().build();
//
//        String req1 = new ObjectMapper().writeValueAsString(reqDto1);
//
//        mockMvc.perform(
//                        post("/api/v1/friend")
//                                .content(req1)
//                                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andDo(print());
//
//    }
//}
