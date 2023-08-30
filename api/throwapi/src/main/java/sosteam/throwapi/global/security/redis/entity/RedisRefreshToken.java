package sosteam.throwapi.global.security.redis.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


import java.time.LocalDateTime;

@Builder
@Getter
@RedisHash(value = "redisRefreshToken", timeToLive = 3600)
@AllArgsConstructor
@NoArgsConstructor
public class RedisRefreshToken {
    @Id
    private String id;
    @Indexed
    private String refreshToken;

    private String accessToken;

}
