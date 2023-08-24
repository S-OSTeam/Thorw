package sosteam.throwapi.domain.order.entity;

import jakarta.persistence.*;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

import java.util.UUID;

@Entity
public class Receipt extends PrimaryKeyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "receipt", fetch = FetchType.LAZY)
    private Gifticon gifticon;
}
