package sosteam.throwapi.domain.store.externalAPI.bizno;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BiznoApiResponse {
    private int resultCode;
    private String resultMsg;
    private int totalCount;
    private List<Items> items;
    @Override
    public String toString() {
        return "BiznoApiResponse{" +
                "resultCode=" + resultCode +
                ", resultMsg='" + resultMsg + '\'' +
                ", totalCount=" + totalCount +
                '}';
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Items {
        private String company;
    }
}
