package sosteam.throwapi.domain.oauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sosteam.throwapi.domain.oauth.entity.AuthTokens;
import sosteam.throwapi.global.service.JwtTokenService;

import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthTokensGenerateService {
    @Value("${jwt.secret.grantType}")
    private static final String BEARER_TYPE = "Bearer";
    @Value("${jwt.secret.access-token-validity-in-seconds}") //43200 초
    private static long ACCESS_TOKEN_EXPIRE_TIME;
    @Value("${jwt.secret.refresh-token-validity-in-seconds}") //1209600 초
    private static long REFRESH_TOKEN_EXPIRE_TIME;
    @Value("${jwt.secret.access-token-kind}") //"accessToken"
    private static String ACCESS_TOKEN_KIND;
    @Value("${jwt.secret.refresh-token-kind}") //"refreshToken"
    private static String REFRESH_TOKEN_KIND;

    private final JwtTokenService jwtTokenService;

    public AuthTokens generate(UUID memberId) {
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String subject = memberId.toString();
        String accessToken = jwtTokenService.generate(subject, ACCESS_TOKEN_KIND, accessTokenExpiredAt);
        String refreshToken = jwtTokenService.generate(subject, REFRESH_TOKEN_KIND, refreshTokenExpiredAt);

        return AuthTokens.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public Long extractMemberId(String accessToken) {
        return Long.valueOf(jwtTokenService.extractSubject(accessToken));
    }
}
