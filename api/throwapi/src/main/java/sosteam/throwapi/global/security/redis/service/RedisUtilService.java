package sosteam.throwapi.global.security.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.user.exception.LoginFailException;
import sosteam.throwapi.global.security.redis.exception.DuplicationLoginException;
import sosteam.throwapi.global.security.redis.exception.NoSuchDataRedisException;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisUtilService {
    private final RedisTemplate redisTemplate;

    public String getData(String key){
        Object result = redisTemplate.opsForValue().get(key);
        if(result != null){
            return result.toString();
        }
        return null;
    }

    public void setData(String key, String value){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        if(!valueOperations.setIfAbsent(key, value, Duration.ofSeconds(3600))){
            throw new DuplicationLoginException();
        }
    }

    public void setAccessToken(String key, String value){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        if(!valueOperations.setIfAbsent(key, value, Duration.ofSeconds(43200))){
            throw new DuplicationLoginException();
        }
    }

    public void setRefreshToken(String key, String value){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        if(!valueOperations.setIfAbsent(key, value, Duration.ofSeconds(1209600))){
            throw new DuplicationLoginException();
        }
    }


    public void setDataExpire(String key, String value, Long duration){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteData(String key){
        if(this.getData(key) == null) throw new NoSuchDataRedisException();
        redisTemplate.delete(key);
    }

}
