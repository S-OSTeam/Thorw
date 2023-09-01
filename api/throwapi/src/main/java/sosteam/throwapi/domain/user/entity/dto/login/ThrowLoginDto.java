package sosteam.throwapi.domain.user.entity.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThrowLoginDto {
    private String inputId;
    private String inputPassword;
}
