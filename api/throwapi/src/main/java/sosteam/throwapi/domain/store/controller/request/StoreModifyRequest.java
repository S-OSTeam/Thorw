package sosteam.throwapi.domain.store.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

@Data
public class StoreModifyRequest {
    @NotNull
    private UUID extStoreId;

    @NotNull
    @Pattern(regexp = "^[0-9]*$", message = "전화번호에 숫자가 아닌 문자가 포함되어 있습니다.")
    private String storePhone;

    @NotNull
    @Pattern(regexp = "^[0-9]{3}-[0-9]{2}-[0-9]{5}|[0-9]{10}$", message = "올바른 사업자 등록번호 형식이 아닙니다.")
    private String crn;

    @NotNull
    @Range(min = -90,max = 90, message = "위도 범위 : -90 ~ 90")
    private Double latitude;

    @NotNull
    @Range(min = -180,max = 180, message = "경도 범위 : -180 ~ 180")
    private Double longitude;

    @NotNull
    @Pattern(regexp = "^[0-9]{5}$", message = "올바른 대한민국 우편번호 형식이 아닙니다.")
    private String zipCode;

    @NotNull
    private String fullAddress;
}
