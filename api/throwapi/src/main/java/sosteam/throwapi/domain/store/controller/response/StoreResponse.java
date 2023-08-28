package sosteam.throwapi.domain.store.controller.response;

import lombok.Data;

@Data
public class StoreResponse {
    private String name;
    private String crn;
    private Double latitude;
    private Double longitude;
    private String zipCode;
    private String fullAddress;

    public StoreResponse(String name, String crn, double latitude, double longitude, String zipCode, String fullAddress) {
        this.name = name;
        this.crn = crn;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zipCode = zipCode;
        this.fullAddress = fullAddress;
    }
}
