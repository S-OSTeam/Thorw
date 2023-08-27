package sosteam.throwapi.global.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/*
 * HTTP 요청 헤더에 관련된 설명:
 *
 * 1. X-Forwarded-For:
 *    - 클라이언트의 원격 IP 주소를 나타내는 헤더.
 *    - 클라이언트와 중간에 있는 프록시 서버들의 IP 주소 목록을 포함할 수 있음.
 *
 * 2. Proxy-Client-IP:
 *    - 클라이언트의 원격 IP 주소를 나타내는 헤더.
 *    - 웹 서버를 통해 클라이언트로 요청을 전송한 프록시 서버의 IP 주소를 포함할 수 있음.
 *
 * 3. WL-Proxy-Client-IP:
 *    - Oracle WebLogic 서버에서 사용하는 헤더.
 *    - 클라이언트의 원격 IP 주소를 나타냄.
 *
 * 4. HTTP_X_FORWARDED_FOR:
 *    - 클라이언트의 원격 IP 주소를 나타내는 헤더.
 *    - 클라이언트와 중간에 있는 프록시 서버들의 IP 주소 목록을 포함할 수 있음.
 *
 * 5. HTTP_X_FORWARDED:
 *    - 클라이언트와 중간에 있는 프록시 서버 간의 연결 정보를 나타내는 헤더.
 *
 * 6. HTTP_X_CLUSTER_CLIENT_IP:
 *    - 클러스터 환경에서 사용하는 헤더.
 *    - 클라이언트의 원격 IP 주소를 나타냄.
 *
 * 7. HTTP_CLIENT_IP:
 *    - 클라이언트의 원격 IP 주소를 나타내는 헤더.
 *
 * 8. HTTP_FORWARDED_FOR:
 *    - 클라이언트의 원격 IP 주소를 나타내는 헤더.
 *    - 클라이언트와 중간에 있는 프록시 서버들의 IP 주소 목록을 포함할 수 있음.
 *
 * 9. HTTP_FORWARDED:
 *    - 클라이언트와 중간에 있는 프록시 서버 간의 연결 정보를 나타내는 헤더.
 *
 * 10. HTTP_VIA:
 *     - HTTP 요청이 프록시 서버를 통해 전송되었음을 나타내는 헤더.
 *     - 프록시 서버의 이름 및 버전 정보를 포함할 수 있음.
 *
 * 11. REMOTE_ADDR:
 *     - 웹 서버가 클라이언트의 원격 IP 주소를 결정하기 위해 사용하는 환경 변수.
 *     - 다른 프록시 헤더가 없는 경우 원격 클라이언트의 IP 주소를 포함함.
 */
public class IPService {

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    public static String getClientIP() {


        if (Objects.isNull(RequestContextHolder.getRequestAttributes())) {
            return "0.0.0.0";
        }
        // 현재 HTTP 요청에 대한 정보를 포함하고 있으며, 이를 통해 요청 헤더, 파라미터, 세션 등을 다룬다.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        for (String header: IP_HEADER_CANDIDATES) {
            String ipFromHeader = request.getHeader(header);
            if (Objects.nonNull(ipFromHeader) && ipFromHeader.isEmpty() && !"unknown".equalsIgnoreCase(ipFromHeader)) {
                String ip = ipFromHeader.split(",")[0];
                return ip;
            }
        }
        return request.getRemoteAddr();
    }
}