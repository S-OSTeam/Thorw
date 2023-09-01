package sosteam.throwapi.domain.user.entity.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sosteam.throwapi.global.entity.Role;
import sosteam.throwapi.global.entity.UserStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCngDto {
    private String inputId;
    private String userPhoneNumber;
    private String email;
}
