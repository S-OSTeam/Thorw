package sosteam.throwapi.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

/**
 * 유저의 개인 정보를 저장하는 엔티티
 * 이름, 핸드폰 번호, 이메일을 입력 받는다.
 */
@Entity
@Getter
@NoArgsConstructor
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

    public UserInfo(String name, String phoneNumber, String email){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public User modifyUser(User user){
        this.user = user;
        return this.user;
    }

    public String modifyPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this.phoneNumber;
    }

    public String modifyEmail(String email) {
        this.email = email;
        return this.email;
    }
}
