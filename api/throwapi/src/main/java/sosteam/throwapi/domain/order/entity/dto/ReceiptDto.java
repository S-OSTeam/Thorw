package sosteam.throwapi.domain.order.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Value;
import sosteam.throwapi.domain.order.entity.GifticonStatus;

import java.util.UUID;

@Value
public class ReceiptDto {
    private UUID id;
    private UUID userId;
    private UUID gifticonId;
    private GifticonStatus gifticonStatus;

    @QueryProjection
    public ReceiptDto(UUID id, UUID userId, UUID gifticonId, GifticonStatus gifticonStatus) {
        this.id = id;
        this.userId = userId;
        this.gifticonId = gifticonId;
        this.gifticonStatus = gifticonStatus;
    }
}
