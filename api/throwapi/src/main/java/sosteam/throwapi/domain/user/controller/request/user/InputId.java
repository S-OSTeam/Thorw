package sosteam.throwapi.domain.user.controller.request.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class InputId {
    @NotNull
    @Pattern(regexp = "^[a-z]+[a-z0-9]{5,19}$", message = "올바른 ID 형식이 아닙니다.")
    private String inputId;
}
