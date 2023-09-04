package sosteam.throwapi.domain.order.kakaoAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GifticonSendResponseDto {
    @JsonProperty("reserve_trace_id")
    private Long reserveTraceId;

    @JsonProperty("template_receivers")
    private List<TemplateReceiver> templateReceivers;

    @Getter
    @AllArgsConstructor
    public static class TemplateReceiver {
        private Integer sequence;

        @JsonProperty("external_key")
        private String externalKey;

        private String name;

        @JsonProperty("receiver_id")
        private String receiverId;

    }
}
