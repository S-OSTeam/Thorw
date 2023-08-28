package sosteam.throwapi.domain.store.log;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sosteam.throwapi.global.service.TimeStamped;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CrnLog extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String ip;

    @Column
    private String email;

    public CrnLog(String ip, String email) {
        this.ip = ip;
        this.email = email;
    }
}
