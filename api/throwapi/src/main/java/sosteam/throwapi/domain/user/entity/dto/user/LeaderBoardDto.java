package sosteam.throwapi.domain.user.entity.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sosteam.throwapi.domain.user.entity.UserInfo;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaderBoardDto {
    private String userName;
    private Long mileage;
}
