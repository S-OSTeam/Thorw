package sosteam.throwapi.domain.store.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAddress is a Querydsl query type for Address
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAddress extends EntityPathBase<Address> {

    private static final long serialVersionUID = 857832480L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAddress address = new QAddress("address");

    public final sosteam.throwapi.global.entity.QPrimaryKeyEntity _super = new sosteam.throwapi.global.entity.QPrimaryKeyEntity(this);

    public final StringPath building = createString("building");

    public final StringPath city = createString("city");

    public final StringPath fullPath = createString("fullPath");

    //inherited
    public final ComparablePath<java.util.UUID> id = _super.id;

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final StringPath load = createString("load");

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final QStore store;

    public final StringPath zipCode = createString("zipCode");

    public QAddress(String variable) {
        this(Address.class, forVariable(variable), INITS);
    }

    public QAddress(Path<? extends Address> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAddress(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAddress(PathMetadata metadata, PathInits inits) {
        this(Address.class, metadata, inits);
    }

    public QAddress(Class<? extends Address> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new QStore(forProperty("store"), inits.get("store")) : null;
    }

}

