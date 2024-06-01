package sosteam.throwapi.domain.user.entity.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingDto {
    private String userName;
    private Long mileage;
    private Long ranking;
}
