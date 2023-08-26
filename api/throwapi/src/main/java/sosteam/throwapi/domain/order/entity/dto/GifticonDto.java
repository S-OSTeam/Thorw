package sosteam.throwapi.domain.order.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Value;

@Value
public class GifticonDto {
    private String couponNumber;
    private String gifticonStatus;

    @QueryProjection
    public GifticonDto(String couponNumber, String gifticonStatus) {
        this.couponNumber = couponNumber;
        this.gifticonStatus = gifticonStatus;
    }
}
