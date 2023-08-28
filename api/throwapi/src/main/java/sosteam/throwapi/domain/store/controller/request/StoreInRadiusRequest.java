package sosteam.throwapi.domain.store.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreInRadiusRequest {
    @NotNull
    @Range(min = -90,max = 90, message = "위도 범위 : -90 ~ 90")
    private Double latitude;

    @NotNull
    @Range(min = -180,max = 180, message = "경도 범위 : -180 ~ 180")
    private Double longitude;

    @NotNull
    @Range(min = 0,max = 5, message = "0km <= 탐색 거리 <= 5km")
    private Double distance;

    public StoreInRadiusRequest(double latitude, double longitude, double distance) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }
}
