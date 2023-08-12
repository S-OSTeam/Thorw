package sosteam.throwapi.global.entity;

import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class PrimaryKeyEntity implements Persistable<UUID> {

    @Id
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id = UlidCreator.getMonotonicUlid().toUuid();

    @Transient
    private Boolean _isNew = true;

    @Override
    public UUID getId(){
        return id;
    }

    @Override
    public boolean isNew(){
        return _isNew;
    }

    @Override
    public boolean equals(Object other){
        if (other == null)
            return false;

        if (other instanceof HibernateProxy && this.getClass() != other.getClass())
        return false;

        return id == getIdentifier(other);
    }

    private Serializable getIdentifier(Object obj){
        if(obj instanceof HibernateProxy)
            return ((PrimaryKeyEntity)((HibernateProxy) obj).getHibernateLazyInitializer().getImplementation()).getId();
        else if(obj instanceof PrimaryKeyEntity)
            return ((PrimaryKeyEntity) obj).getId();
        throw new IllegalArgumentException("이상한 친구가 접근했습니다!");
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @PostPersist
    @PostLoad
    protected void load(){
        _isNew = false;
    }
}
