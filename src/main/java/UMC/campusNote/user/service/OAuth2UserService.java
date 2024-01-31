package UMC.campusNote.user.service;

import UMC.campusNote.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Role generate
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");

        // nameAttributeKey
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();


        log.warn(oAuth2User.getAttributes().toString());

        // 회원 가입에 필요한 정보 추가
        String oauthClientName=userRequest.getClientRegistration().getClientName(); //registraion Id를 가져올 수 있음(kakao, naver...)
        Map<String, Object> attribute = new HashMap<>();

        if(oauthClientName.equals("kakao")){
            String clientId=oAuth2User.getName();
            LinkedHashMap<String, String> properties = oAuth2User.getAttribute("properties");
            String img = properties.get("profile_image");

            if(clientId==null||img==null) throw new OAuth2AuthenticationException("FAIL");

            attribute.put("id",clientId);
            attribute.put("img", img);

        }

        return new DefaultOAuth2User(authorities, attribute, userNameAttributeName);
    }
}
