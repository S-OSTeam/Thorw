package sosteam.throwapi.domain.store.controller.response;

import lombok.Data;

@Data
public class SearchStoreResponse {
    private String name;
    private String companyRegistrationNumber;
    private Double latitude;
    private Double longitude;
    private String zipCode;
    private String fullAddress;

    public SearchStoreResponse(String name, String companyRegistrationNumber, double latitude, double longitude, String zipCode, String fullAddress) {
        this.name = name;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zipCode = zipCode;
        this.fullAddress = fullAddress;
    }
}
