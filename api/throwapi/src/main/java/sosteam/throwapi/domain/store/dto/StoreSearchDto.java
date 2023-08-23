package sosteam.throwapi.domain.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.locationtech.jts.geom.LineString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreSearchDto {
    @NotNull
    @Range(min = -90,max = 90, message = "위도 범위 : -90 ~ 90")
    private Double latitude;

    @NotNull
    @Range(min = -180,max = 180, message = "경도 범위 : -180 ~ 180")
    private Double longitude;

    @NotNull
    @Max(value = 5, message = "검색 최대 거리 5km")
    private Double distance;

    private LineString lineString;

    public StoreSearchDto(double latitude, double longitude, double distance) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.lineString = null;
    }
}
