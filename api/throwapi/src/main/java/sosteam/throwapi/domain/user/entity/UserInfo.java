package sosteam.throwapi.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

/**
 * 유저의 개인 정보를 저장하는 엔티티
 * 이름, 핸드폰 번호, 이메일을 입력 받는다.
 */
@Entity
@Getter
public class UserInfo extends PrimaryKeyEntity {
    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    @NotNull
    @Email
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public String setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this.phoneNumber;
    }

    public String setEmail(String email) {
        this.email = email;
        return this.email;
    }
}
