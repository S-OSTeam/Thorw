package sosteam.throwapi.domain.user.controller.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MileageRequest {
    @NotNull
    private Long mileage;
}
