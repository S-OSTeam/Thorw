package sosteam.throwapi.domain.user.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRequest {
    @NotNull
    @Pattern(regexp = "^[a-z]+[a-z0-9]{5,19}$", message = "올바른 ID 형식이 아닙니다.")
    private String inputId;
}
