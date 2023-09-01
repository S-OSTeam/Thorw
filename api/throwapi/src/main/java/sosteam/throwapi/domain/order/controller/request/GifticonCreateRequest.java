package sosteam.throwapi.domain.order.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class GifticonCreateRequest {
    @NotNull
    private UUID userId;

    @NotNull
    private String productName;

    @NotNull
    private Long orderNumber;
}
