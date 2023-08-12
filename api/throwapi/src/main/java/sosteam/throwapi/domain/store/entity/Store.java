package sosteam.throwapi.domain.store.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

@Getter
@Entity
public class Store extends PrimaryKeyEntity {

    @NotNull
    private String name;

    @NotNull
    private String companyRegistrationNumber;

    @NotNull
    private String secondPassword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne(mappedBy = "store", fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;
}
