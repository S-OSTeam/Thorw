package sosteam.throwapi.domain.order.repository.repoCustomImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sosteam.throwapi.domain.order.entity.*;

import sosteam.throwapi.domain.order.repository.repoCustom.ReceiptCustomRepository;

import java.util.*;

import static sosteam.throwapi.domain.order.entity.QReceipt.receipt;

@Repository
@RequiredArgsConstructor
public class ReceiptCustomRepositoryImpl implements ReceiptCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Set<Receipt>> searchByUserId(UUID userId) {
        return Optional.ofNullable(new HashSet<>(jpaQueryFactory.selectFrom(receipt)
                .join(receipt.gifticon).fetchJoin()   // Fetch join for Gifticon
                .join(receipt.user).fetchJoin()       // Fetch join for User
                .where(receipt.user.id.eq(userId))
                .fetch()));
    }
}
