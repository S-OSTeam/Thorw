package sosteam.throwapi.domain.store.controller.response;

import lombok.Data;

@Data
public class StoreResponse {
    private String storeName;
    private String storePhone;
    private String crn;
    private Double latitude;
    private Double longitude;
    private String zipCode;
    private String fullAddress;

    public StoreResponse(String storeName, String storePhone,String crn, Double latitude, Double longitude, String zipCode, String fullAddress) {
        this.storeName = storeName;
        this.storePhone = storePhone;
        this.crn = crn;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zipCode = zipCode;
        this.fullAddress = fullAddress;
    }
}
