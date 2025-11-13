package com.base.rest.service.impl;

import com.base.common.bo.VerificationCodeBo;
import com.base.common.enums.VerificationCodeActionTypeEnum;
import com.base.common.enums.VerificationCodeStateEnum;
import com.base.common.repo.MemberRepo;
import com.base.common.repo.VerificationCodeRepo;
import com.base.rest.handel.ApiException;
import com.base.rest.service.EmailService;
import com.base.rest.service.VerificationCodeService;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final VerificationCodeRepo verificationCodeRepo;
    private final EmailService emailService;
    private final SecureRandom secureRandom;
    private final MemberRepo memberRepo;

    private static final long EXPIRE_TIME_MINUTE = 10L; // 過期時間 10分鐘

    @Override
    public void sendVerificationCode(String email, VerificationCodeActionTypeEnum actionType) {

        String subject = Strings.EMPTY;
        String content = Strings.EMPTY;

        String randomVerificationCode = this.getRandomVerificationCode();

        switch (actionType) {
            case LOGIN:

                if (Objects.isNull(memberRepo.findMemberByEmail(email))) {
                    throw new ApiException("該信箱尚未註冊，請先完成註冊");
                }

                subject = "Ryan DenDen 登入驗證碼";
                content = String.format("您的登入驗證碼: [%s]", randomVerificationCode);
                break;
            case REGISTER:

                if (Objects.nonNull(memberRepo.findMemberByEmail(email))) {
                    throw new ApiException("該信箱已被註冊");
                }

                subject = "Ryan DenDen 註冊驗證碼";
                content = String.format("您的註冊驗證碼: [%s]", randomVerificationCode);
                break;
        }

        Timestamp expireTime = Timestamp.from(Instant.now().plus(EXPIRE_TIME_MINUTE, ChronoUnit.MINUTES));

        if (emailService.sendEmail(email, subject, content)) {
            verificationCodeRepo.insertVerificationCode(
                VerificationCodeBo.builder()
                    .code(randomVerificationCode)
                    .state(VerificationCodeStateEnum.ACTIVE)
                    .actionType(actionType)
                    .expireTime(expireTime)
                    .email(email)
                    .build()
            );
        }
    }

    @Override
    public void validateVerificationCode(String email, VerificationCodeActionTypeEnum actionType, String verificationCode) {

        VerificationCodeBo verificationCodeBo =
            Optional.ofNullable(verificationCodeRepo.findVerificationCodeByEmailAndActionType(email, actionType, verificationCode))
                .orElseThrow(() -> new ApiException("驗證碼不正確"));

        Timestamp now = Timestamp.from(Instant.now());

        if (now.after(verificationCodeBo.getExpireTime())) {
            throw new ApiException("該驗證碼已過期, 請重新取得驗證碼");
        }

        verificationCodeBo.setState(VerificationCodeStateEnum.USED);

        // 校驗成功後 把它變為已使用
        verificationCodeRepo.updateVerificationCode(
            verificationCodeBo
        );
    }

    /**
     * 產生隨機驗證碼
     */
    private String getRandomVerificationCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(secureRandom.nextInt(10)); // 每次產生 0～9
        }
        return sb.toString();
    }
}
