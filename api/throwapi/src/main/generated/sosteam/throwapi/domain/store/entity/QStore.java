package sosteam.throwapi.domain.store.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStore is a Querydsl query type for Store
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStore extends EntityPathBase<Store> {

    private static final long serialVersionUID = -1255738355L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStore store = new QStore("store");

    public final sosteam.throwapi.global.entity.QPrimaryKeyEntity _super = new sosteam.throwapi.global.entity.QPrimaryKeyEntity(this);

    public final QAddress address;

    public final StringPath companyRegistrationNumber = createString("companyRegistrationNumber");

    //inherited
    public final ComparablePath<java.util.UUID> id = _super.id;

    public final QLocation location;

    public final StringPath name = createString("name");

    public final StringPath secondPassword = createString("secondPassword");

    public final sosteam.throwapi.domain.user.entity.QUser user;

    public QStore(String variable) {
        this(Store.class, forVariable(variable), INITS);
    }

    public QStore(Path<? extends Store> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStore(PathMetadata metadata, PathInits inits) {
        this(Store.class, metadata, inits);
    }

    public QStore(Class<? extends Store> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address"), inits.get("address")) : null;
        this.location = inits.isInitialized("location") ? new QLocation(forProperty("location")) : null;
        this.user = inits.isInitialized("user") ? new sosteam.throwapi.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

