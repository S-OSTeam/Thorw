package sosteam.throwapi.domain.store.log;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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

    @Column
    private UUID userId;

public CrnLog(String ip, UUID userId) {
        this.ip = ip;
        this.userId = userId;
    }
}
