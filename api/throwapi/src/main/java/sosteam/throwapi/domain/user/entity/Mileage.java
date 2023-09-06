package sosteam.throwapi.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

@Entity
public class Mileage extends PrimaryKeyEntity {

    @NotNull
    private Long amount;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Long setAmount(Long amount) {
        this.amount = amount;
        return this.amount;
    }
}
