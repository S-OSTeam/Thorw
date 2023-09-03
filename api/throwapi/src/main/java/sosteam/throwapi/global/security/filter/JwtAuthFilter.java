package sosteam.throwapi.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.repository.UserRepository;
import sosteam.throwapi.domain.user.service.TokenService;
import sosteam.throwapi.global.security.redis.service.RedisUtilService;
import sosteam.throwapi.global.service.JwtTokenService;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final RedisUtilService redisUtilService;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal (
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )throws IOException, ServletException {
        String accessToken = request.getHeader("access_token");
        if(accessToken != null){
            this.checkAccessToken(accessToken);
        }

        filterChain.doFilter(request, response);
    }

    private boolean checkAccessToken(String accessToken){
        String inputId = jwtTokenService.extractSubject(accessToken);
        User user = userRepository.searchByInputId(inputId);
        UUID memberId = user.getId();

        String isLogout = redisUtilService.getData(inputId);
        String refreshToken = redisUtilService.getData(memberId.toString());
        log.debug("isLogout = {}, refreshToken = {}", isLogout, refreshToken);

        if(!(accessToken==null && accessToken.isEmpty()) && isLogout == null && refreshToken != null){
            try {
                if(jwtTokenService.validateToken(accessToken)){
                    Authentication authentication = jwtTokenService.getAuthentication(user);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return true;
                }
            } catch (Exception e){
                log.error("checkAccessToken fail = {}", e.getMessage());
                return false;
            }
        }
        return false;
    }
}
