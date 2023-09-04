package sosteam.throwapi.domain.order.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ReceiptSearchRequest {
    @NotNull
    private UUID userId;
}
