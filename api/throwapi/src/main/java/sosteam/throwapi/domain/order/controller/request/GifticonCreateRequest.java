package sosteam.throwapi.domain.order.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class GifticonCreateRequest {
    @NotNull
    private String userInputId;

    @NotNull
    private String productName;
}
