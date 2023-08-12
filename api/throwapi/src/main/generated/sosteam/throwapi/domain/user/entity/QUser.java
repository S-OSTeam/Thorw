package sosteam.throwapi.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -101605091L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final sosteam.throwapi.global.entity.QPrimaryKeyEntity _super = new sosteam.throwapi.global.entity.QPrimaryKeyEntity(this);

    //inherited
    public final ComparablePath<java.util.UUID> id = _super.id;

    public final StringPath inputId = createString("inputId");

    public final StringPath inputPassword = createString("inputPassword");

    public final QMileage mileage;

    public final ListPath<sosteam.throwapi.domain.order.entity.Order, sosteam.throwapi.domain.order.entity.QOrder> orders = this.<sosteam.throwapi.domain.order.entity.Order, sosteam.throwapi.domain.order.entity.QOrder>createList("orders", sosteam.throwapi.domain.order.entity.Order.class, sosteam.throwapi.domain.order.entity.QOrder.class, PathInits.DIRECT2);

    public final EnumPath<sosteam.throwapi.global.entity.Role> role = createEnum("role", sosteam.throwapi.global.entity.Role.class);

    public final ListPath<sosteam.throwapi.domain.store.entity.Store, sosteam.throwapi.domain.store.entity.QStore> stores = this.<sosteam.throwapi.domain.store.entity.Store, sosteam.throwapi.domain.store.entity.QStore>createList("stores", sosteam.throwapi.domain.store.entity.Store.class, sosteam.throwapi.domain.store.entity.QStore.class, PathInits.DIRECT2);

    public final QUserInfo userInfo;

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mileage = inits.isInitialized("mileage") ? new QMileage(forProperty("mileage"), inits.get("mileage")) : null;
        this.userInfo = inits.isInitialized("userInfo") ? new QUserInfo(forProperty("userInfo"), inits.get("userInfo")) : null;
    }

}

