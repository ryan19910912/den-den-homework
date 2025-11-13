package com.base.rest.service.impl;

import com.base.rest.service.BlacklistService;
import com.base.rest.utils.JwtUtils;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlacklistServiceImpl implements BlacklistService {

    // Key: Token 字串, Value: Token 的過期時間 (Epoch Milliseconds)
    private final Map<String, Long> blacklist = new ConcurrentHashMap<>();

    private final JwtUtils jwtUtils;

    @Override
    public void add(String token) {
        try {
            // 從 Token 中解析出過期時間 (java.util.Date)
            Date expiration = jwtUtils.getExpirationDateFromToken(token);

            if (Objects.isNull(expiration)) {
                return;
            }

            // 將 Date 轉換為毫秒數 (Epoch Milliseconds) 儲存
            long expiryTimeMillis = expiration.getTime();

            blacklist.put(token, expiryTimeMillis);

        } catch (Exception e) {
            // 處理 Token 解析失敗的情況
            log.error("無法解析 Token 以取得過期時間: {}", e.getMessage());
        }
    }

    @Override
    public void cleanUpBlacklist() {
        long now = Instant.now().toEpochMilli();
        // 超過過期時間的就清除掉
        blacklist.entrySet().removeIf(entry -> entry.getValue() < now);
    }

    @Override
    public boolean isBlacklisted(String token) {
        return blacklist.containsKey(token);
    }
}
