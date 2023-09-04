package sosteam.throwapi.global.security.redis.repository;

import org.springframework.data.repository.CrudRepository;
import sosteam.throwapi.global.security.redis.entity.RedisTokens;

public interface RefreshTokenRedisRepository extends CrudRepository<RedisTokens, String> {
    RedisTokens findByRefreshToken(String inputId);
}
