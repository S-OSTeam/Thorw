package sosteam.throwapi.domain.user.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sosteam.throwapi.domain.oauth.entity.Tokens;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.login.LogoutDto;
import sosteam.throwapi.domain.user.entity.dto.login.ThrowLoginDto;
import sosteam.throwapi.domain.user.exception.LoginFailException;
import sosteam.throwapi.domain.user.repository.UserRepository;
import sosteam.throwapi.global.security.redis.repository.RefreshTokenRedisRepository;
import sosteam.throwapi.global.security.redis.service.RedisUtilService;
import sosteam.throwapi.global.service.JwtTokenService;
import sosteam.throwapi.global.service.TokensGenerateService;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokensGenerateService tokensGenerateService;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final RedisUtilService redisUtilService;
    private final UserSeaerchService userSearchService;
    private final JwtTokenService jwtTokenService;

    public Tokens throwLogin(ThrowLoginDto throwLoginDto){ //아이디 비번 일치 하는지 확인 후 일치 하면 Tokens 을
        // inputId 를 이용해 User 정보를 불러 옴
        // 그 후 존재 하는 유저인지 예외 처리
        User user = userRepository.searchByInputId(throwLoginDto.getInputId());
        if(user == null){
            log.debug("login fail no such user");
            throw new LoginFailException();
        }

        //user 계정의 상태를 확인 한다
        userSearchService.isUserStatusNormal(user.getUserStatus());


        String userPW = user.getPassword();
        log.debug("userPw = {}", userPW);
        if(!passwordEncoder.matches(throwLoginDto.getInputPassword(), userPW)){
            log.debug("login fail long password");
            throw new LoginFailException();
        }
        Tokens tokens = tokensGenerateService.generate(user.getId(), user.getInputId());

//        redisUtilService.setData(user.getId().toString(), tokens.getRefreshToken());

        // key : UUID, value : refreshToken 으로 redis 에 50400 초 동안 저장
        // reissue 시 보안을 위해 저장
        redisUtilService.setRefreshToken(user.getId().toString(), tokens.getRefreshToken());
        // key : inputId, value : "login" 으로 redis 에 7200 초 동안 저장
        // 중복 로그인을 방지 하기 위함
        redisUtilService.setAccessToken(user.getInputId(), "login");

        return tokens;
    }

    @Transactional
    public void logout(LogoutDto logoutDto){
        String accessToken = logoutDto.getAccessToken();
        log.debug("accessToken = {}", accessToken);
        // 토큰으로 inputId 를 뽑아냄 -> 그 후 User 를 구함
        //extractSubject 에서 유효 처리 함
        String inputId = jwtTokenService.extractSubject(accessToken);
        User user = userRepository.searchByInputId(inputId);
        String memberId = user.getId().toString();
        log.debug("inputId = {}", inputId);
        log.debug("Id = {}", user.getId().toString());


        // 구한 정보(UUID) 로 refreshToken 이 저장 되어 있는지 확인 후 있으면 삭제 처리
        if(memberId != null) {
            // 로그인을 위해 저장 헀던 정보를 삭제 해 줌
            // 우선 refreshToken 삭제
            redisUtilService.deleteData(memberId);
            // 그 후 login 삭제
            redisUtilService.deleteData(user.getInputId());
            log.debug("delete success");
        }

        // 남은 유효 시간을 구해 옴
        Long expiration = jwtTokenService.getExpiration(accessToken);
        log.debug(expiration.toString());

        log.debug("expiration = {}", expiration);
        redisUtilService.setDataExpire(accessToken, "logout", expiration);
    }
}
