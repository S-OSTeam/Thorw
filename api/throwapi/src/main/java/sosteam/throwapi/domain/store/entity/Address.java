package sosteam.throwapi.domain.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

@Getter
@Entity
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

    public Address(){};

    public Address(Point location, double latitude, double longitude, String fullAddress, String zipCode) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fullAddress = fullAddress;
        this.zipCode = zipCode;
    }
    //    @NotNull
//    private String roadAddress;
//
//    @NotNull String roadZipCode;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    private Store store;
}
