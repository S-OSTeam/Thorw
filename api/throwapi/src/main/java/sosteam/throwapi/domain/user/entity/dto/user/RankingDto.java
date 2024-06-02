package sosteam.throwapi.domain.user.entity.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sosteam.throwapi.domain.store.entity.Store;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingDto {
    private String inputId;
    private String userName;
    private Long mileage;
    private Long ranking;
}
