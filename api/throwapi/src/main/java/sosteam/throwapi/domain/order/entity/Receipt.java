package sosteam.throwapi.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Enumerated(EnumType.STRING)
    private GifticonStatus gifticonStatus; //주문상태 [ORDER, CANCEL]

    public void modifyGifticonStatus(GifticonStatus gifticonStatus) {
        this.gifticonStatus = gifticonStatus;
    }

    //==연관관계 메서드==//
    public void modifyUser(User user) {
        this.user=user;
        user.getReceipts().add(this);
    }

    public void modifyGifticon(Gifticon gifticon) {
        this.gifticon = gifticon;
        gifticon.setReceipt(this);
    }

    //==비즈니스 로직==//
    /**
     * 주문 생성
     */
    public static Receipt createReceipt(User user, Gifticon gifticon){
        Receipt receipt=new Receipt();
        receipt.modifyUser(user);
        receipt.modifyGifticon(gifticon);
        receipt.modifyGifticonStatus(GifticonStatus.SOLD);
        return receipt;
    }

    /**
     * 주문 취소
     */
    public void cancel(){
        if(gifticon.getGifticonStatus()==GifticonStatus.SOLD){
            throw new IllegalStateException("현재 팔려있는 기프티콘입니다.");
        }

        this.modifyGifticonStatus(GifticonStatus.WAIT);
    }
}
