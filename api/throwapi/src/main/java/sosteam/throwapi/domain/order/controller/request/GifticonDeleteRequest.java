package sosteam.throwapi.domain.order.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GifticonDeleteRequest {
    @NotNull
    private String giftTraceId; // 주문 완료 후 생성되는 수신자 단위의 선물 번호
}
