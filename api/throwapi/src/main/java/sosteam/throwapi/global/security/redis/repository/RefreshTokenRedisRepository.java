package sosteam.throwapi.global.security.redis.repository;

import org.springframework.data.repository.CrudRepository;
import sosteam.throwapi.global.security.redis.entity.RedisRefreshToken;

public interface RefreshTokenRedisRepository extends CrudRepository<RedisRefreshToken, Long> {
    RedisRefreshToken findByRefreshToken(String refreshToken);
}
