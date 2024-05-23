package com.example.mediready.global.config.redis;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate stringRedisTemplate;

    // key로 Redis에 저장된 데이터 가져오는 메소드
    public String getData(String key) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    // key-value를 제한시간을 두어 저장하는 메소드
    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    // key에 해당하는 데이터를 가져오고 그 데이터가 value와 일치하는지 확인, 일치하면 redis에서 해당 key-value 쌍을 삭제하고 true 반환
    public boolean checkData(String key, String value) {
        String data = getData(key);

        if (data != null && data.equals(value)) {
            deleteData(key);
            return true;
        }
        return false;
    }

    // redis에서 key 값에 해당하는 데이터를 삭제
    public void deleteData(String key) {
        stringRedisTemplate.delete(key);
    }

    public void addToBlacklist(String token, long expirationTime) {
        stringRedisTemplate.opsForValue()
            .set(token, "blacklisted", expirationTime, TimeUnit.MILLISECONDS);
    }

    public boolean isBlacklisted(String token) {
        return stringRedisTemplate.hasKey(token);
    }
}