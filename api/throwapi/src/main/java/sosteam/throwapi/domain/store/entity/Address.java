package sosteam.throwapi.domain.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends PrimaryKeyEntity {

    @NotNull
    @Column(columnDefinition = "point",name = "location")
    private Point location;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    @NotNull
    private String fullAddress;

    @NotNull
    private String zipCode;

    public Address(Point location, double latitude, double longitude, String fullAddress, String zipCode) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fullAddress = fullAddress;
        this.zipCode = zipCode;
    }

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    private Store store;

    public Store modifyStore(Store store){
        this.store = store;
        return this.store;
    }
}
