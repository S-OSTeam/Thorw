package sosteam.throwapi.domain.user.entity.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PWCheckDto {
    private String inputId;
    private String inputPassword;
}
