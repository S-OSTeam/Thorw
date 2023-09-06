package sosteam.throwapi.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Mileage extends PrimaryKeyEntity {

    @NotNull
    private Long amount;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Mileage(Long amount) {
        this.amount = amount;
    }


    public Long modifyAmount(Long amount) {
        this.amount = amount;
        return this.amount;
    }

    public User modifyUser(User user) {
        this.user = user;
        return this.user;
    }

}
