package sosteam.throwapi.domain.user.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.user.UserCngDto;
import sosteam.throwapi.domain.user.repository.custom.UserRepositoryCustom;

import java.time.LocalDateTime;
import java.util.UUID;

import static sosteam.throwapi.domain.user.entity.QUser.user;
import static sosteam.throwapi.domain.user.entity.QUserInfo.userInfo;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public User searchBySNSId(String snsId) {
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

    @Override
    public User searchById(UUID id){
        User result = queryFactory
                .selectFrom(user)
                .where(user.id.eq(id))
                .fetchOne();
        return result;
    }

    @Override
    @Transactional
    public Long updateByInputId(UserCngDto userCngDto) {
        UUID userId = this.searchUUIDByInputId(userCngDto.getInputId());
        return queryFactory
                .update(userInfo)
                .set(userInfo.userName, userCngDto.getUserName())
                .set(userInfo.email, userCngDto.getEmail())
                .set(userInfo.userPhoneNumber, userCngDto.getUserPhoneNumber())
                .set(userInfo.updatedAt, LocalDateTime.now())
                .where(userInfo.user.id.eq(userId))
                .execute();
    }

    @Override
    public UUID searchUUIDByInputId(String inputId){
        User result = queryFactory
                .selectFrom(user)
                .join(user.userInfo, userInfo)
                .where(user.inputId.eq(inputId))
                .fetchOne();
        return result.getId();
    }

    @Override
    public User searchByInputId(String inputId){
        User result = queryFactory
                .selectFrom(user)
                .join(user.userInfo, userInfo)
                .where(user.inputId.eq(inputId))
                .fetchOne();
        return result;
    }
}
