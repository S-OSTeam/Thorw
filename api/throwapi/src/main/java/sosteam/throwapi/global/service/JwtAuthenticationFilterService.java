package sosteam.throwapi.global.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilterService{
//        extends GenericFilterBean {
//    private final JwtTokenService jwtTokenService;
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//        //1. JWT RefreshToken 추출
//        String refreshToken = servletRequest.getAttribute("refreshToken");
//
//        //TODO:: 무조건 탄다, permitAll()은 이 뒤에서 검사 => 즉, 여기서 걸리는 이유는 Authorization 토큰이 있기 때문에 validateToken() 메서드에서 걸리는 것
//
//        //2. validateToken 메서드로 토큰 유효성 검사
//        if (refreshToken != null && jwtTokenService.validateToken(refreshToken)) {
//            if (!((HttpServletRequest) servletRequest).getRequestURI().equals("/v1/user/reissue")) {
//                Authentication authentication = jwtTokenService.getAuthentication(refreshToken);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
}
