package sosteam.throwapi.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMileage is a Querydsl query type for Mileage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMileage extends EntityPathBase<Mileage> {

    private static final long serialVersionUID = -2050502088L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMileage mileage = new QMileage("mileage");

    public final sosteam.throwapi.global.entity.QPrimaryKeyEntity _super = new sosteam.throwapi.global.entity.QPrimaryKeyEntity(this);

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    //inherited
    public final ComparablePath<java.util.UUID> id = _super.id;

    public final QUser user;

    public QMileage(String variable) {
        this(Mileage.class, forVariable(variable), INITS);
    }

    public QMileage(Path<? extends Mileage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMileage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMileage(PathMetadata metadata, PathInits inits) {
        this(Mileage.class, metadata, inits);
    }

    public QMileage(Class<? extends Mileage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

