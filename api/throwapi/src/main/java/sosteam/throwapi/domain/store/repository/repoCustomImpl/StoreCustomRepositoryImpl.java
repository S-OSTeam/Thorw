package sosteam.throwapi.domain.store.repository.repoCustomImpl;

import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sosteam.throwapi.domain.store.entity.Address;
import sosteam.throwapi.domain.store.entity.QAddress;
import sosteam.throwapi.domain.store.entity.QStore;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.domain.store.entity.dto.StoreInRadiusDto;
import sosteam.throwapi.domain.store.entity.dto.StoreDto;
import sosteam.throwapi.domain.store.repository.repoCustom.StoreCustomRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
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
    public Optional<Set<StoreDto>> searchStoreInRadius(StoreInRadiusDto storeInRadiusDto) {
        StringTemplate mbrContains = Expressions.stringTemplate(
                "MBRContains({0},{1})",
                storeInRadiusDto.getLineString(),
                qAddress.location
        );
        String likeStr = storeInRadiusDto.getTrashType().replace("0", "_");
        Set<StoreDto> result = new HashSet<>(jpaQueryFactory
                .select(
                        Projections.constructor(
                                StoreDto.class,
                                qStore.storeName,
                                qStore.storePhone,
                                qStore.companyRegistrationNumber,
                                qAddress.latitude,
                                qAddress.longitude,
                                qAddress.zipCode,
                                qAddress.fullAddress,
                                qStore.trashType
                        )
                )
                .from(qStore)
                .innerJoin(qStore.address, qAddress)
                .where(mbrContains.eq("1"),qStore.trashType.like(likeStr))
                .fetch());

        return Optional.of(result);
    }

    @Override
    public Optional<StoreDto> searchByCRN(String crn) {
        StoreDto result = jpaQueryFactory
                .select(
                        Projections.constructor(
                                StoreDto.class,
                                qStore.storeName,
                                qStore.storePhone,
                                qStore.companyRegistrationNumber,
                                qAddress.latitude,
                                qAddress.longitude,
                                qAddress.zipCode,
                                qAddress.fullAddress,
                                qStore.trashType
                        )
                )
                .from(qStore)
                .innerJoin(qStore.address, qAddress)
                .where(qStore.companyRegistrationNumber.eq(crn))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Store> searchByExtStoreId(UUID uuid) {
        Store store = jpaQueryFactory
                .selectFrom(qStore)
                .where(qStore.extStoreId.eq(uuid))
                .fetchOne();
        return Optional.ofNullable(store);
    }

    @Override
    public Optional<Address> searchAddressByStore(UUID uuid) {
        Address address = jpaQueryFactory
                .selectFrom(qAddress)
                .innerJoin(qAddress.store,qStore)
                .where(qStore.id.eq(uuid))
                .fetchOne();
        return Optional.ofNullable(address);
    }

    @Override
    public Optional<Set<StoreDto>> searchByName(String name) {

        Set<StoreDto> result = new HashSet<>(
                jpaQueryFactory
                        .select(
                                Projections.constructor(
                                        StoreDto.class,
                                        qStore.storeName,
                                        qStore.storePhone,
                                        qStore.companyRegistrationNumber,
                                        qAddress.latitude,
                                        qAddress.longitude,
                                        qAddress.zipCode,
                                        qAddress.fullAddress,
                                        qStore.trashType
                                )
                        )
                        .from(qStore)
                        .innerJoin(qStore.address,qAddress)
                        .where(qStore.storeName.contains(name))
                        .fetch()
        );
        return Optional.of(result);
    }
}
