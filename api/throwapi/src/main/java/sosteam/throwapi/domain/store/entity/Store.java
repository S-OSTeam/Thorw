package sosteam.throwapi.domain.store.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

@Getter
@Entity
public class Store extends PrimaryKeyEntity {

    @NotNull
    private String name;

    @NotNull
    private String companyRegistrationNumber;

    public Store() {};

    public Store(String name, String companyRegistrationNumber) {
        this.name = name;
        this.companyRegistrationNumber = companyRegistrationNumber;
    }

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;


    @Setter
    @OneToOne(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
}
