package sosteam.throwapi.domain.order.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Value;
import sosteam.throwapi.domain.order.entity.GifticonStatus;

@Value
public class ReceiptDto {
    private GifticonStatus gifticonStatus;

    @QueryProjection
    public ReceiptDto(GifticonStatus gifticonStatus) {
        this.gifticonStatus = gifticonStatus;
    }
}
