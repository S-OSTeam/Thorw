package sosteam.throwapi.domain.mail.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AuthMail extends PrimaryKeyEntity {

    @NotNull
    @Column(length = 6)
    public String authCode;

    @CreatedDate
    @Column(updatable = false)
    public LocalDateTime createdAt;

    @NotNull
    public LocalDateTime endAt;

    @NotNull
    public String email;

    @NotNull
    public boolean isSuccess;

    public AuthMail(String authCode, LocalDateTime endAt, String email, boolean isSuccess) {
        this.authCode = authCode;
        this.endAt = endAt;
        this.email = email;
        this.isSuccess = isSuccess;
    }
}
