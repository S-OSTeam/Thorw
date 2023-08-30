package sosteam.throwapi.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sosteam.throwapi.domain.order.entity.Receipt;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;
import sosteam.throwapi.global.entity.Role;
import sosteam.throwapi.global.entity.UserStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 유저의 로그인 정보 엔티티
 * id, password를 저장 후 역할은 enum을 이용하여 부여
 */

@Getter
@Entity
@NoArgsConstructor
public class User extends PrimaryKeyEntity implements UserDetails {

    @NotNull
    @Column(unique = true)
    private String inputId;

    @NotNull
    private String inputPassword;

    @Column(unique = true)
    private String snsId;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private SNSCategory sns;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserInfo userInfo;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Mileage mileage;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Store> stores;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Receipt> receipts;

    public User(String inputId, String inputPassword, String snsId, SNSCategory sns){
        this.inputId = inputId;
        this.inputPassword = inputPassword;
        this.snsId = snsId;
        this.sns = sns;
    }

    public UserStatus modifyUserStatus(UserStatus userStatus){
        this.userStatus = userStatus;
        return this.userStatus;
    }

    public Role modifyRole(Role role) {
        this.role = role;
        return this.role;
    }

    public UserInfo modifyUserInfo(UserInfo userInfo){
        this.userInfo = userInfo;
        return this.userInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
        return simpleGrantedAuthorities;
    }


    @Override
    public String getPassword() {
        return this.inputPassword;
    }

    @Override
    public String getUsername() {
        return this.getId().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
