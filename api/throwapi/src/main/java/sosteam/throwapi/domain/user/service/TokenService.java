package sosteam.throwapi.domain.user.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.oauth.entity.Tokens;
import sosteam.throwapi.domain.oauth.exception.NotValidateTokenException;
import sosteam.throwapi.domain.oauth.service.AuthTokensGenerateService;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.ReissueTokensDto;
import sosteam.throwapi.domain.user.repository.UserRepository;
import sosteam.throwapi.global.service.JwtTokenService;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final AuthTokensGenerateService tokensGenerateService;


    public Tokens reissueTokens(ReissueTokensDto tokensDto){
        String subject = jwtTokenService.extractSubject(tokensDto.getRefreshToken());
        log.debug("subject = {}", subject);
        UUID memberId = UUID.fromString(subject);
        log.debug("memberId = {}", memberId);

        User user = userRepository.searchById(memberId);
        if(user == null){
            throw new NotValidateTokenException();
        }
        return tokensGenerateService.generate(memberId, user.getInputId());
    }
}
