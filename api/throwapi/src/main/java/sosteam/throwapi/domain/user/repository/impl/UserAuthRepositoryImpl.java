package sosteam.throwapi.domain.user.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import sosteam.throwapi.domain.user.entity.QUserAuth;
import sosteam.throwapi.domain.user.repository.custom.UserAuthRepositoryCustom;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UserAuthRepositoryImpl implements UserAuthRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QUserAuth userAuth = QUserAuth.userAuth;

    //현재로부터 10분 이내의 인증 성공 내역이 있는지 확인한다.
    @Override
    public Boolean existSuccessByEmail(String email) {

        return queryFactory
                .select(userAuth.isSuccess)
                .from(userAuth)
                .where(userAuth.createdAt.goe(LocalDateTime.now().minusMinutes(10)),
                        userAuth.email.eq(email),
                        userAuth.isSuccess.eq(true))
                .fetchOne();
    }
}
