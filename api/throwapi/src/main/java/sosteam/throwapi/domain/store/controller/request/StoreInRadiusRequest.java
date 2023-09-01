package sosteam.throwapi.domain.store.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @NotNull
    @Pattern(regexp = "^[0-1]{5}$", message = "일반쓰레기|병|플라스틱|종이|캔 : Provide(o):1 Provide(x):0")
    private String trashType;

    public StoreInRadiusRequest(double latitude, double longitude, double distance,String trashType) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.trashType = trashType;
    }
}
