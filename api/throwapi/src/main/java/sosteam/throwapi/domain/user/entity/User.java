package sosteam.throwapi.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sosteam.throwapi.domain.order.entity.Receipt;
import sosteam.throwapi.domain.store.entity.Store;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;
import sosteam.throwapi.global.entity.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 유저의 로그인 정보 엔티티
 * id, password를 저장 후 역할은 enum을 이용하여 부여
 */

@Getter
@Entity
public class User extends PrimaryKeyEntity implements UserDetails {

    @NotNull
    private String inputId;

    @NotNull
    private String inputPassword;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserInfo userInfo;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Mileage mileage;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Store> stores;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Receipt> receipts;

    public Role setRole(Role role) {
        this.role = role;
        return this.role;
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
