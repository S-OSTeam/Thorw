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

    @QueryProjection
    public GifticonDto(UUID id, String couponNumber, UUID itemId, UUID receiptId) {
        this.id = id;
        this.couponNumber = couponNumber;
        this.itemId = itemId;
        this.receiptId = receiptId;
    }
}
