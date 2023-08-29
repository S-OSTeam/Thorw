package sosteam.throwapi.domain.store.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import sosteam.throwapi.domain.store.controller.response.StoreResponse;

@Data
@NoArgsConstructor
public class StoreDto {
    private String storeName;
    private String storePhone;
    private String crn;
    private Double latitude;
    private Double longitude;
    private String zipCode;
    private String fullAddress;

    @QueryProjection
    public StoreDto(String storeName, String storePhone, String crn, Double latitude, Double longitude, String zipCode, String fullAddress) {
        this.storeName = storeName;
        this.storePhone = storePhone;
        this.crn = crn;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zipCode = zipCode;
        this.fullAddress = fullAddress;
    }

    public StoreResponse toResponse(){
        return new StoreResponse(
                this.getStoreName(),
                this.getStorePhone(),
                this.getCrn(),
                this.getLatitude(),
                this.getLongitude(),
                this.getZipCode(),
                this.getFullAddress()
        );
    }
}
