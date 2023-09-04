package sosteam.throwapi.domain.user.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class UserAuth extends PrimaryKeyEntity {

    private String email;

    @CreatedDate
    private LocalDateTime createdAt;

    private boolean isSuccess;

    public UserAuth(String email, boolean isSuccess) {
        this.email = email;
        this.isSuccess = isSuccess;
    }
}
