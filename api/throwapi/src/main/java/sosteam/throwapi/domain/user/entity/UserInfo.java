package sosteam.throwapi.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

import java.time.LocalDateTime;

/**
 * 유저의 개인 정보를 저장하는 엔티티
 * 이름, 핸드폰 번호, 이메일을 입력 받는다.
 */
@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserInfo extends PrimaryKeyEntity {
    @NotNull
    private String userName;

    @NotNull
    @Column(unique = true)
    private String userPhoneNumber;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public UserInfo(String name, String phoneNumber, String email) {
        this.userName = name;
        this.userPhoneNumber = phoneNumber;
        this.email = email;
    }

    public User modifyUser(User user) {
        this.user = user;
        return this.user;
    }

    public String modifyPhoneNumber(String phoneNumber) {
        this.userPhoneNumber = phoneNumber;
        return this.userPhoneNumber;
    }

    public String modifyEmail(String email) {
        this.email = email;
        return this.email;
    }
}
