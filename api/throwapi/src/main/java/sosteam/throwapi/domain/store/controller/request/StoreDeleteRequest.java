package sosteam.throwapi.domain.store.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class StoreDeleteRequest {
    @NotNull
    private UUID extStoreId;
}

