package sosteam.throwapi.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

@Entity
@Getter
@NoArgsConstructor
public class Receipt extends PrimaryKeyEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "receipt", fetch = FetchType.LAZY)
    private Gifticon gifticon;

    @NotNull
    private ReceiptStatus receiptStatus; // 기프티콘 상태 [SALE,CONFIRMED, SOLD]

    public Receipt(User user) {
        this.user = user;
        this.receiptStatus = ReceiptStatus.SALE;
    }

    public void modifyGifticon(Gifticon gifticon) {
        this.gifticon = gifticon;
        gifticon.modifyReceipt(this);
    }

    public void modifyUser(User user) {
        this.user = user;
    }
}
