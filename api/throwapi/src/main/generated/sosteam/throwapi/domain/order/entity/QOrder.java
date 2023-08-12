package sosteam.throwapi.domain.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = 942443821L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final sosteam.throwapi.global.entity.QPrimaryKeyEntity _super = new sosteam.throwapi.global.entity.QPrimaryKeyEntity(this);

    public final QGifticon gifticon;

    //inherited
    public final ComparablePath<java.util.UUID> id = _super.id;

    public final sosteam.throwapi.domain.user.entity.QUser user;

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.gifticon = inits.isInitialized("gifticon") ? new QGifticon(forProperty("gifticon"), inits.get("gifticon")) : null;
        this.user = inits.isInitialized("user") ? new sosteam.throwapi.domain.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

