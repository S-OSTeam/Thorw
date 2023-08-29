package sosteam.throwapi.domain.order.repository.repoCustomImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sosteam.throwapi.domain.order.entity.Gifticon;
import sosteam.throwapi.domain.order.repository.repoCustom.GifticonCustomRepository;

import java.util.Optional;

import static sosteam.throwapi.domain.order.entity.QGifticon.gifticon;

@Repository
@RequiredArgsConstructor
public class GifticonCustomRepositoryImpl implements GifticonCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Gifticon> searchByGiftTraceId(String giftTraceId) {
        Gifticon foundGifticon = jpaQueryFactory.selectFrom(gifticon)
                .where(gifticon.giftTraceId.eq(giftTraceId))
                .fetchOne();
        return Optional.ofNullable(foundGifticon);
    }
}
