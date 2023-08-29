package sosteam.throwapi.domain.store.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sosteam.throwapi.domain.user.entity.User;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends PrimaryKeyEntity {

    @NotNull
    private String storeName;

    @NotNull
    private String storePhone;


    @NotNull
    private String companyRegistrationNumber;

    public Store(String storeName, String storePhone, String companyRegistrationNumber) {
        this.storeName = storeName;
        this.storePhone = storePhone;
        this.companyRegistrationNumber = companyRegistrationNumber;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToOne(
            mappedBy = "store",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "address_id")
    private Address address;

    public Address modifyAddress(Address address){
        this.address = address;
        return this.address;
    }
}
