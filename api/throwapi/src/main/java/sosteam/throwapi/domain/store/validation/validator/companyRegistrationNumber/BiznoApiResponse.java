package sosteam.throwapi.domain.store.validation.validator.companyRegistrationNumber;

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
    private List<Item> items;

    public static class Item {
        private String company;
        private String bno;
        private String cno;
        private String bsttcd;
        private String bstt;
        private String TaxTypeCd;
        private String taxtype;
        private String EndDt;

    }

    @Override
    public String toString() {
        return "BiznoApiResponse{" +
                "resultCode=" + resultCode +
                ", resultMsg='" + resultMsg + '\'' +
                ", totalCount=" + totalCount +
                ", items=" + items +
                '}';
    }
}
