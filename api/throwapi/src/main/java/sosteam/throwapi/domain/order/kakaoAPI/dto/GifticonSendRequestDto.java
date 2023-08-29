package sosteam.throwapi.domain.order.kakaoAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GifticonSendRequestDto {

    @JsonProperty("template_token")
    private String templateToken;

    @JsonProperty("receiver_type")
    private String receiverType;

    private List<Receiver> receivers;

    @JsonProperty("success_callback_url")
    private String successCallbackUrl;

    @JsonProperty("fail_callback_url")
    private String failCallbackUrl;

    @JsonProperty("template_order_name")
    private String templateOrderName;

    @JsonProperty("external_order_id")
    private String externalOrderId;

    @Getter
    @AllArgsConstructor
    public static class Receiver {
        @JsonProperty("receiver_id")
        private String receiverId;

        private String name;
    }
}
