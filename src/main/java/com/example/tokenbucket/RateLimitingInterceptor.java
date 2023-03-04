package com.example.tokenbucket;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @Value("${ratelimiting.limit}")
  private int limit;

  @Value("${ratelimiting.interval}")
  private int interval;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String ipAddress = request.getRemoteAddr();
    String key = "rate:" + ipAddress;

    // cập nhật số lượng yêu cầu đã được gửi trong khoảng thời gian này
    Long count = redisTemplate.opsForValue().increment(key, 1L);
    // nếu số lượng yêu cầu vượt quá giới hạn, trả về mã lỗi
    if (count != null && count > limit) {
      response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
      return false;
    }
    // đặt thời gian hết hạn cho khoảng thời gian tiếp theo
    redisTemplate.expire(key, interval, TimeUnit.SECONDS);
    return true;
  }
}

