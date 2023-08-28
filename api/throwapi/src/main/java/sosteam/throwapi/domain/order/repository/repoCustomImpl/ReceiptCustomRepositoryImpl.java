package sosteam.throwapi.domain.order.repository.repoCustomImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sosteam.throwapi.domain.order.entity.Receipt;
import sosteam.throwapi.domain.order.repository.repoCustom.ReceiptCustomRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static sosteam.throwapi.domain.order.entity.QReceipt.receipt;

@Repository
@RequiredArgsConstructor
public class ReceiptCustomRepositoryImpl implements ReceiptCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Set<Receipt> searchByUserId(UUID userId) {
        return new HashSet<>(jpaQueryFactory.selectFrom(receipt)
                .where(receipt.user.id.eq(userId))
                .fetch());
    }
}
