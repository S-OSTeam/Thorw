package sosteam.throwapi.domain.store.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class StoreNameRequest {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z가-힣\\s0-9]*$", message = "영어, 한글, 숫자, 공백만 허용됩니다.")
    private String storeName;
}
