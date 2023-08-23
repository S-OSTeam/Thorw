package sosteam.throwapi.domain.store.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.LineString;
import sosteam.throwapi.domain.store.validation.ValidLatitude;
import sosteam.throwapi.domain.store.validation.ValidLongitude;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchStoreInRadiusDto {
    @ValidLatitude
    private Double latitude;

    @ValidLongitude
    private Double longitude;

    @NotNull
    @Max(value = 5, message = "검색 최대 거리 5km")
    private Double distance;

    private LineString lineString;

    public SearchStoreInRadiusDto(double latitude, double longitude, double distance) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.lineString = null;
    }
}
