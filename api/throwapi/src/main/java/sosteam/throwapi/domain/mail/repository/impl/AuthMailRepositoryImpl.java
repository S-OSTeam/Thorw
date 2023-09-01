package sosteam.throwapi.domain.mail.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import sosteam.throwapi.domain.mail.entity.QAuthMail;
import sosteam.throwapi.domain.mail.repository.custom.AuthMailRepositoryCustom;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class AuthMailRepositoryImpl implements AuthMailRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private QAuthMail authMail;

    @Override
    public String searchSendCodeByEmail(String email) {
        return jpaQueryFactory.select(authMail.authCode)
                .from(authMail)
                .where(authMail.endAt.goe(LocalDateTime.now()))
                .fetchOne();
    }
}
