package sosteam.throwapi.domain.user.controller.request.user;

import lombok.Data;

@Data
public class MileageRequest {
    private String inputId;
    private Long mileage;
}
