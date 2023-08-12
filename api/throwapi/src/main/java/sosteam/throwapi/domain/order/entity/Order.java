package sosteam.throwapi.domain.order.entity;

import jakarta.persistence.*;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

@Entity
public class Order extends PrimaryKeyEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    private Gifticon gifticon;
}
