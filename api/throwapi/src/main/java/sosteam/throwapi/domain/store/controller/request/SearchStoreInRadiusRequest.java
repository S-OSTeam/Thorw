package sosteam.throwapi.domain.store.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sosteam.throwapi.domain.store.validation.ValidDemandedDistance;
import sosteam.throwapi.domain.store.validation.ValidLatitude;
import sosteam.throwapi.domain.store.validation.ValidLongitude;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchStoreInRadiusRequest {
    @ValidLatitude
    private Double latitude;

    @ValidLongitude
    private Double longitude;

    @ValidDemandedDistance
    private Double distance;

    public SearchStoreInRadiusRequest(double latitude, double longitude, double distance) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }
}
