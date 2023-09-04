package sosteam.throwapi.domain.user.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.user.UserCngDto;
import sosteam.throwapi.domain.user.repository.custom.UserRepositoryCustom;

import java.time.LocalDateTime;
import java.util.UUID;

import static sosteam.throwapi.domain.user.entity.QUser.user;
import static sosteam.throwapi.domain.user.entity.QUserInfo.userInfo;

@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private final EntityManager em;

    @Override
    public User searchBySNSId(String snsId) {
        User result = queryFactory
                .selectFrom(user)
                .where(user.snsId.eq(snsId))
                .fetchOne();
        return result;
    }

    @Override
    public boolean existBySNSId(String snsId) {
        User result = queryFactory
                .selectFrom(user)
                .where(user.snsId.eq(snsId))
                .fetchOne();

        return result != null;
    }

    @Override
    public User searchById(UUID id) {
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
        long result = queryFactory
                .update(userInfo)
                .set(userInfo.userName, userCngDto.getUserName())
                .set(userInfo.email, userCngDto.getEmail())
                .set(userInfo.userPhoneNumber, userCngDto.getUserPhoneNumber())
                .set(userInfo.updatedAt, LocalDateTime.now())
                .where(userInfo.user.id.eq(userId))
                .execute();

        em.flush();
        em.clear();

        return result;
    }

    @Override
    public UUID searchUUIDByInputId(String inputId) {
        User result = queryFactory
                .selectFrom(user)
                .join(user.userInfo, userInfo)
                .fetchJoin()
                .where(user.inputId.eq(inputId))
                .fetchOne();
        return result.getId();
    }

    @Override
    public User searchByInputId(String inputId) {
        User result = queryFactory
                .selectFrom(user)
                .join(user.userInfo, userInfo)
                .fetchJoin()
                .where(user.inputId.eq(inputId))
                .fetchOne();
        return result;
    }

    @Override
    public User searchByEmail(String email) {
        return queryFactory.select(user)
                .from(user)
                .innerJoin(user.userInfo, userInfo)
                .fetchJoin()
                .where(userInfo.email.eq(email))
                .fetchOne();
    }

    @Override
    @Transactional
    public Long updatePwdByUserId(UUID userId, String pwd) {
        Long result = queryFactory.update(user)
                .set(user.inputPassword, pwd)
                .where(user.id.eq(userId))
                .execute();

        em.flush();
        em.clear();

        log.debug("update pwd : {}", result);

        return result;
    }
}
