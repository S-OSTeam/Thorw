package sosteam.throwapi.domain.store.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String secondPassword;

    public Store() {};

    public Store(String name, String companyRegistrationNumber, String secondPassword) {
        this.name = name;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.secondPassword = secondPassword;
    }

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;


    @Setter
    @OneToOne(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
}
