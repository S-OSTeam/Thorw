package sosteam.throwapi.domain.store.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

@Data
public class StoreDeleteRequest {
    @NotNull
    private UUID extStoreId;
}

