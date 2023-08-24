package sosteam.throwapi.domain.store.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.LineString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchStoreInRadiusDto {
    private Double latitude;

    private Double longitude;

    private Double distance;

    private LineString lineString;

    public SearchStoreInRadiusDto(double latitude, double longitude, double distance) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.lineString = null;
    }
}
