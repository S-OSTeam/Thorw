package sosteam.throwapi.domain.store.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class StoreModifyDto {
    private UUID extStoreId;
    private String storeName;
    private String storePhone;
    private String crn;
    private Double latitude;
    private Double longitude;
    private String zipCode;
    private String fullAddress;
    private String trashType;
}
