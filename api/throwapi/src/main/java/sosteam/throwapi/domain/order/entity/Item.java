package sosteam.throwapi.domain.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

import java.util.List;

@Getter
//@Entity
public class Item extends PrimaryKeyEntity {

    @NotNull
    private String name;

    @NotNull
    private Long price;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<Gifticon> gifticons;
}
