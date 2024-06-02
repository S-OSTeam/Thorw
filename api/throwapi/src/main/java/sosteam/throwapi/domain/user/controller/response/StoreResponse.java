package sosteam.throwapi.domain.user.controller.response;

import lombok.Data;
import sosteam.throwapi.global.entity.Role;

import java.util.UUID;

@Data
public class StoreResponse {
    private String storeName;
    private UUID extStoreId;

    public StoreResponse(String storeName, UUID extStoreId) {
        this.storeName = storeName;
        this.extStoreId = extStoreId;
    }

}
