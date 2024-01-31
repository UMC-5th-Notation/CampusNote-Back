package UMC.campusNote.user.service;

import UMC.campusNote.common.exception.GeneralException;
import UMC.campusNote.user.dto.JoinReqDto;
import UMC.campusNote.user.dto.JoinResDto;
import UMC.campusNote.user.entity.User;
import UMC.campusNote.user.repository.UserRepository;
import UMC.campusNote.user.utils.JwtUtil;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import static UMC.campusNote.common.code.status.ErrorStatus.USER_ALREADY_EXIST;
import static UMC.campusNote.user.entity.Role.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private Long expiredAtMs=1000*60*60L; //1000*60*60L;


    public String createToken(String userName){
        //인증 과정 필요(userName과 password를 사용한 인증 구현 필요)
        return jwtUtil.createJwt(userName, expiredAtMs);
    }

    public boolean isPresentUser(String clientId){
        User user = userRepository.findByClientId(clientId);

        if(user==null) return false;

        return true;
    }

    public JoinResDto join(JoinReqDto joinReqDto){

        User existUser = userRepository.findByClientId(joinReqDto.getClientId());

        if(existUser!=null) throw new GeneralException(USER_ALREADY_EXIST);

        User newUser = joinReqDto.toEntity();
        userRepository.save(newUser);

        String token=createToken(newUser.getName());

        return JoinResDto.fromEntity(token);
    }
}
