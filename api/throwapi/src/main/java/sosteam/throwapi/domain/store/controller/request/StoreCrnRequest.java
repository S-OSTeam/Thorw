package sosteam.throwapi.domain.store.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class StoreCrnRequest {
    @NotNull
    @Pattern(regexp = "^[0-9]{3}-[0-9]{2}-[0-9]{5}|[0-9]{10}$", message = "올바른 사업자 등록번호 형식이 아닙니다.")
    private String crn;
}
