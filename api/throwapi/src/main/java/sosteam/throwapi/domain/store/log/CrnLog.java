package sosteam.throwapi.domain.store.log;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import sosteam.throwapi.global.service.TimeStamped;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CrnLog extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String ip;

    @Column(name = "user_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID userId;

public CrnLog(String ip, UUID userId) {
        this.ip = ip;
        this.userId = userId;
    }
}
