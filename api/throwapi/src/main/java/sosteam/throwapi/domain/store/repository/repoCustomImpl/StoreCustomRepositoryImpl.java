package sosteam.throwapi.domain.store.repository.repoCustomImpl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sosteam.throwapi.domain.store.dto.StoreResponseDto;
import sosteam.throwapi.domain.store.dto.StoreSaveDto;
import sosteam.throwapi.domain.store.dto.StoreSearchDto;
import sosteam.throwapi.domain.store.entity.QAddress;
import sosteam.throwapi.domain.store.entity.QStore;
import sosteam.throwapi.domain.store.repository.repoCustom.StoreCustomRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class StoreCustomRepositoryImpl implements StoreCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QStore qStore;
    private final QAddress qAddress;
    @Autowired

    public StoreCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.qStore = QStore.store;
        this.qAddress = QAddress.address;
    }

    @Override
    public Optional<List<StoreResponseDto>> search(StoreSearchDto storeSearchDto) {
        StringTemplate mbrContains = Expressions.stringTemplate(
                "MBRContains({0},{1})",
                storeSearchDto.getLineString(),
                qAddress.location
        );

        List<StoreResponseDto> result = jpaQueryFactory
                .select(
                        Projections.constructor(
                                StoreResponseDto.class,
                                qStore.name,
                                qAddress.latitude,
                                qAddress.longitude,
                                qAddress.zipCode,
                                qAddress.fullAddress
                        )
                )
                .from(qStore)
                .innerJoin(qStore.address, qAddress)
                .where(mbrContains.eq("1"))
                .fetch()
                .stream().distinct().toList();

        return Optional.of(result);
    }
}
