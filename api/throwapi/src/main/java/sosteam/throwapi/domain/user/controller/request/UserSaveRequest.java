package sosteam.throwapi.domain.user.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveRequest {
    private String inputId;
    private String inputPassWord;
    private String snsId;
    private String sns;
    private String userStatus;
    private String role;

    private String name;
    private String phoneNumber;
    private String email;
}
