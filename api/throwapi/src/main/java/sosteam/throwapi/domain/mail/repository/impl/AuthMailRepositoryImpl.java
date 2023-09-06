package sosteam.throwapi.domain.mail.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import sosteam.throwapi.domain.mail.entity.QAuthMail;
import sosteam.throwapi.domain.mail.repository.custom.AuthMailRepositoryCustom;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class AuthMailRepositoryImpl implements AuthMailRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QAuthMail authMail = QAuthMail.authMail;
    private final EntityManager em;


    @Override
    public String searchSendCodeByEmail(String email) {
        return jpaQueryFactory.select(authMail.authCode)
                .from(authMail)
                .where(authMail.endAt.goe(LocalDateTime.now()), authMail.email.eq(email), authMail.isSuccess.eq(false))
                .fetchOne();
    }

    @Override
    @Transactional
    public long modifyIsSuccessByEmail(String email) {
        long isSuccess = jpaQueryFactory
                .update(authMail)
                .set(authMail.isSuccess, true)
                .where(authMail.email.eq(email), authMail.isSuccess.eq(false), authMail.endAt.goe(LocalDateTime.now()))
                .execute();

        em.flush();
        em.clear();

        return isSuccess;
    }
}
