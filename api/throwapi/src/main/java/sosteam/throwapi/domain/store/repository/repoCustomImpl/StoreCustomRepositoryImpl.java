package sosteam.throwapi.domain.store.repository.repoCustomImpl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sosteam.throwapi.domain.store.entity.QAddress;
import sosteam.throwapi.domain.store.entity.QStore;
import sosteam.throwapi.domain.store.entity.dto.SearchStoreInRadiusDto;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;
import sosteam.throwapi.domain.store.repository.repoCustom.StoreCustomRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
    public Optional<Set<StoreDto>> search(SearchStoreInRadiusDto searchStoreInRadiusDto) {
        StringTemplate mbrContains = Expressions.stringTemplate(
                "MBRContains({0},{1})",
                searchStoreInRadiusDto.getLineString(),
                qAddress.location
        );

        Set<StoreDto> result = new HashSet<>(jpaQueryFactory
                .select(
                        Projections.constructor(
                                StoreDto.class,
                                qStore.name,
                                qStore.companyRegistrationNumber,
                                qAddress.latitude,
                                qAddress.longitude,
                                qAddress.zipCode,
                                qAddress.fullAddress
                        )
                )
                .from(qStore)
                .innerJoin(qStore.address, qAddress)
                .where(mbrContains.eq("1"))
                .fetch());

        return Optional.of(result);
    }

    @Override
    public StoreDto findByRegistrationNumber(String registrationNumber) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                StoreDto.class,
                                qStore.name,
                                qStore.companyRegistrationNumber,
                                qAddress.latitude,
                                qAddress.longitude,
                                qAddress.zipCode,
                                qAddress.fullAddress
                        )
                )
                .from(qStore)
                .innerJoin(qStore.address, qAddress)
                .where(qStore.companyRegistrationNumber.eq(registrationNumber))
                .fetchOne();
    }
}
