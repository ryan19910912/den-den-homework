package com.base.rest.scheduled;

import com.base.rest.service.BlacklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlacklistScheduled {

    private final BlacklistService blacklistService;

    /**
     * 定時任務：每 1 分鐘檢查並清除過期的黑名單 Token
     */
    @Scheduled(fixedRate = 60000) // 600000 毫秒 = 1 分鐘
    public void cleanUpBlacklist() {
        blacklistService.cleanUpBlacklist();
    }
}
