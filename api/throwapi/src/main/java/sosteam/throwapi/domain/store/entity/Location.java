package sosteam.throwapi.domain.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

import java.util.List;

@Getter
@Entity
public class Location extends PrimaryKeyEntity {

    @NotNull
    private String sector;

    @NotNull
    private double y;

    @NotNull
    private double x;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<Store> stores;
}
