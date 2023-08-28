package sosteam.throwapi.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

@Entity
public class Receipt extends PrimaryKeyEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "receipt", fetch = FetchType.LAZY)
    private Gifticon gifticon;

    @NotNull
    private ReceiptStatus receiptStatus; // 기프티콘 상태 [SALE,CONFIRMED, SOLD]
}
