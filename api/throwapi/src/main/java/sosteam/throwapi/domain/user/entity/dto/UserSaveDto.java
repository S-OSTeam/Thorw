package sosteam.throwapi.domain.user.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sosteam.throwapi.global.entity.Role;
import sosteam.throwapi.domain.user.entity.SNSCategory;
import sosteam.throwapi.global.entity.UserStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveDto {
    private String inputId;
    private String inputPassword;
    private String inputPasswordCheck;
    private String snsId;
    private SNSCategory snsCategory;
    private UserStatus userStatus;
    private Role role;

    private String userName;
    private String userPhoneNumber;
    private String email;
}

