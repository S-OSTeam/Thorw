package sosteam.throwapi.domain.order.repository.repoCustomImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sosteam.throwapi.domain.order.entity.Item;
import sosteam.throwapi.domain.order.repository.repoCustom.ItemCustomRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static sosteam.throwapi.domain.order.entity.QItem.item;

@Repository
@RequiredArgsConstructor
public class ItemCustomRepositoryImpl implements ItemCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Item> searchByProductName(String productName) {
        Item foundItem = queryFactory.selectFrom(item)
                .where(item.productName.eq(productName))
                .fetchOne();
        return Optional.ofNullable(foundItem);
    }

    @Override
    public Optional<Set<Item>> searchByProductNameContaining(String productName) {
        Set<Item> foundItems = new HashSet<>(queryFactory.selectFrom(item)
                .where(item.productName.contains(productName))
                .fetch());
        return Optional.ofNullable(foundItems);
    }
}
