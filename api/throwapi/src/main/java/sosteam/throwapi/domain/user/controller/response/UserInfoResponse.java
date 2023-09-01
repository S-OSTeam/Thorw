package sosteam.throwapi.domain.user.controller.response;

import lombok.Data;
import sosteam.throwapi.global.entity.Role;

@Data
public class UserInfoResponse {
    private String inputId;
    private Role role;
    private String userName;
    private String userPhoneNumber;
    private String email;

    public UserInfoResponse(String inputId, Role role, String userName, String userPhoneNumber, String email){
        this.inputId = inputId;
        this.role = role;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.email = email;
    }
}
