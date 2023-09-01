package sosteam.throwapi.domain.store.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import sosteam.throwapi.domain.store.controller.response.StoreResponse;

import java.util.UUID;

@Data
@NoArgsConstructor
public class StoreDto {
    private UUID extStoreId;
    private String storeName;
    private String storePhone;
    private String crn;
    private Double latitude;
    private Double longitude;
    private String zipCode;
    private String fullAddress;
    private String trashType;
    @QueryProjection
    public StoreDto(UUID extStoreId, String storeName, String storePhone, String crn, Double latitude, Double longitude, String zipCode, String fullAddress, String trashType) {
        this.extStoreId = extStoreId;
        this.storeName = storeName;
        this.storePhone = storePhone;
        this.crn = crn;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zipCode = zipCode;
        this.fullAddress = fullAddress;
        this.trashType = trashType;
    }

    public StoreResponse toResponse(){
        return new StoreResponse(
                this.getExtStoreId(),
                this.getStoreName(),
                this.getStorePhone(),
                this.getCrn(),
                this.getLatitude(),
                this.getLongitude(),
                this.getZipCode(),
                this.getFullAddress(),
                this.getTrashType()
        );
    }
}
