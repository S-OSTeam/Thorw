package sosteam.throwapi.domain.user.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import sosteam.throwapi.domain.user.entity.QUserAuth;
import sosteam.throwapi.domain.user.repository.custom.UserAuthRepositoryCustom;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UserAuthRepositoryImpl implements UserAuthRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private QUserAuth userAuth;

    @Override
    public Boolean existSuccessByEmail(String email) {
        //현재로부터 10분 이내의 인증 성공 내역이 있는지 확인한다.
        return queryFactory.select(userAuth.isSuccess)
                .from(userAuth)
                .where(userAuth.createdAt.goe(LocalDateTime.now().minusMinutes(10)).and(userAuth.isSuccess.eq(true)))
                .fetchOne();
    }
}
