package sosteam.throwapi.domain.order.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Value;

import java.util.UUID;

@Value
public class ReceiptDto {
    private UUID id;
    private UUID userId;
    private UUID gifticonId;

    @QueryProjection
    public ReceiptDto(UUID id, UUID userId, UUID gifticonId) {
        this.id = id;
        this.userId = userId;
        this.gifticonId = gifticonId;
    }
}
