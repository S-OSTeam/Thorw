package sosteam.throwapi.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPrimaryKeyEntity is a Querydsl query type for PrimaryKeyEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QPrimaryKeyEntity extends EntityPathBase<PrimaryKeyEntity> {

    private static final long serialVersionUID = -1483028224L;

    public static final QPrimaryKeyEntity primaryKeyEntity = new QPrimaryKeyEntity("primaryKeyEntity");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public QPrimaryKeyEntity(String variable) {
        super(PrimaryKeyEntity.class, forVariable(variable));
    }

    public QPrimaryKeyEntity(Path<? extends PrimaryKeyEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPrimaryKeyEntity(PathMetadata metadata) {
        super(PrimaryKeyEntity.class, metadata);
    }

}

