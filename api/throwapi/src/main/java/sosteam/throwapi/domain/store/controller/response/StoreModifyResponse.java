package sosteam.throwapi.domain.store.controller.response;

import lombok.Data;

@Data
public class StoreModifyResponse {
    private String storeName;
    private String storePhone;
    private String crn;
    private Double latitude;
    private Double longitude;
    private String zipCode;
    private String fullAddress;
    private String trashType;

    public StoreModifyResponse(String storeName, String storePhone, String crn, Double latitude, Double longitude, String zipCode, String fullAddress,String trashType) {
        this.storeName = storeName;
        this.storePhone = storePhone;
        this.crn = crn;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zipCode = zipCode;
        this.fullAddress = fullAddress;
        this.trashType = trashType;
    }
}
