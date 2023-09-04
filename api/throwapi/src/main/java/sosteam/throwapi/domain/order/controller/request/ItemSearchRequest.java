package sosteam.throwapi.domain.order.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemSearchRequest {
    @NotNull
    private String productName;
}
