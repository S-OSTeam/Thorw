package sosteam.throwapi.domain.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

@Getter
@Entity
public class Address extends PrimaryKeyEntity {

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    @NotNull
    private String city;

    @NotNull
    private String road;

    @NotNull
    private String building;

    @NotNull
    private String zipCode;

    @NotNull
    private String fullPath;

    @OneToOne(fetch = FetchType.LAZY)
    private Store store;
}
