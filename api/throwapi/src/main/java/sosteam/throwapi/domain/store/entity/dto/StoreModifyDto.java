package sosteam.throwapi.domain.store.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreModifyDto {
    private String storeCode;
    private String storeName;
    private String storePhone;
    private String crn;
    private Double latitude;
    private Double longitude;
    private String zipCode;
    private String fullAddress;

    public String concat() {
        String lat = String.valueOf(this.latitude);
        String lon = String.valueOf(this.longitude);
        return this.storeName +
                this.storePhone +
                this.crn +
                lat +
                lon +
                this.zipCode +
                this.fullAddress;
    }
}
