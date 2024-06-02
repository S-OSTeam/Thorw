package sosteam.throwapi.domain.user.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import sosteam.throwapi.domain.store.entity.Store;

import java.util.List;

@Data
@AllArgsConstructor
public class RankingResponse {
    private String inputId;
    private String userName;
    private Long mileage;
    private Long ranking;
}
