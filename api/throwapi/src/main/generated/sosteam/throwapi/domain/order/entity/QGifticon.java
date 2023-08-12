package sosteam.throwapi.domain.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGifticon is a Querydsl query type for Gifticon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGifticon extends EntityPathBase<Gifticon> {

    private static final long serialVersionUID = -241580182L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGifticon gifticon = new QGifticon("gifticon");

    public final sosteam.throwapi.global.entity.QPrimaryKeyEntity _super = new sosteam.throwapi.global.entity.QPrimaryKeyEntity(this);

    public final StringPath couponNumber = createString("couponNumber");

    //inherited
    public final ComparablePath<java.util.UUID> id = _super.id;

    public final QItem item;

    public final QOrder order;

    public QGifticon(String variable) {
        this(Gifticon.class, forVariable(variable), INITS);
    }

    public QGifticon(Path<? extends Gifticon> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGifticon(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGifticon(PathMetadata metadata, PathInits inits) {
        this(Gifticon.class, metadata, inits);
    }

    public QGifticon(Class<? extends Gifticon> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.order = inits.isInitialized("order") ? new QOrder(forProperty("order"), inits.get("order")) : null;
    }

}

