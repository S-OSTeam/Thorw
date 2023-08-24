package sosteam.throwapi.domain.order.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Value;

import java.util.UUID;

@Value
public class GifticonDto {
    private UUID id;
    private String couponNumber;
    private UUID itemId;
    private UUID receiptId;
    private String gifticonStatus;

    @QueryProjection
    public GifticonDto(UUID id, String couponNumber, UUID itemId, UUID receiptId, String gifticonStatus) {
        this.id = id;
        this.couponNumber = couponNumber;
        this.itemId = itemId;
        this.receiptId = receiptId;
        this.gifticonStatus=gifticonStatus;
    }
}
