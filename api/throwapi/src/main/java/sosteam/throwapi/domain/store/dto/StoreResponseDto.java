package sosteam.throwapi.domain.store.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StoreResponseDto {
    private String name;
    private String companyRegistrationNumber;
    private Double latitude;
    private Double longitude;
    private String zipCode;
    private String fullAddress;

    @QueryProjection
    public StoreResponseDto(String name, String companyRegistrationNumber,double latitude, double longitude, String zipCode, String fullAddress) {
        this.name = name;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zipCode = zipCode;
        this.fullAddress = fullAddress;
    }
}
