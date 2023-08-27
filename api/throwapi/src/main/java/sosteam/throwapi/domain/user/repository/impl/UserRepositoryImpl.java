package sosteam.throwapi.domain.user.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.repository.custom.UserRepositoryCustom;

import static sosteam.throwapi.domain.user.entity.QUser.user;
import static sosteam.throwapi.domain.user.entity.QUserInfo.userInfo;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public User findBySNSId(String snsId) {
        User result = queryFactory
                .selectFrom(user)
                .where(user.snsId.eq(snsId))
                .fetchOne();
        return result;
    }

    @Override
    public boolean existBySNSId(String snsId){
        User result = queryFactory
                .selectFrom(user)
                .where(user.snsId.eq(snsId))
                .fetchOne();

        if(result == null){
            return false;
        }
        return true;
    }
}
