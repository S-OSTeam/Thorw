package sosteam.throwapi.domain.store.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoreDto {
    private String name;

    private String companyRegistrationNumber;

    private Double latitude;

    private Double longitude;

    private String zipCode;

    private String fullAddress;

    @QueryProjection
    public StoreDto(String name, String companyRegistrationNumber, Double latitude, Double longitude, String zipCode, String fullAddress) {
        this.name = name;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zipCode = zipCode;
        this.fullAddress = fullAddress;
    }
}
