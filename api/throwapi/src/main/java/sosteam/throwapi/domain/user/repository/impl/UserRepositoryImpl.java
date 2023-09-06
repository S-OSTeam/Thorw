package sosteam.throwapi.domain.user.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.domain.user.entity.dto.user.UserCngDto;
import sosteam.throwapi.domain.user.repository.custom.UserRepositoryCustom;
import sosteam.throwapi.global.entity.UserStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static sosteam.throwapi.domain.user.entity.QMileage.mileage;
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
    @Transactional
    public Long updateUserStatusByInputId(String inputId, UserStatus userStatus) {
        UUID userId = this.searchUUIDByInputId(inputId);
        long result = queryFactory
                .update(user)
                .set(user.userStatus, userStatus)
                .where(user.id.eq(userId))
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

    @Override
    public Long findRankByMileage(Long userMileage) {
        // mileage보다 더 높은 mileage를 가진 사용자의 수를 조회
        long countHigherMileage = queryFactory
                .selectFrom(mileage)
                .where(mileage.amount.gt(userMileage))
                .fetch().size();

        // 순위는 "더 높은 마일리지를 가진 사용자의 수 + 1"로 계산
        return countHigherMileage + 1;
    }


    @Override
    public Set<User> searchTop10UsersByMileage() {
        return new HashSet<>(queryFactory
                .selectFrom(user)
                .join(user.mileage, mileage)
                .orderBy(mileage.amount.desc())
                .limit(10)
                .fetch());
    }

    @Override
    @Transactional
    public Long modifyMileageByInputId(String inputId, Long acMileage) {
        UUID userId = this.searchUUIDByInputId(inputId);
        long result = queryFactory
                .update(mileage)
                .set(mileage.amount, acMileage)
                .where(mileage.user.id.eq(userId))
                .execute();

        em.flush();
        em.clear();

        return result;
    }
}
