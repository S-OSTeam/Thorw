package sosteam.throwapi.domain.store.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sosteam.throwapi.domain.store.validation.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreSaveDto {
    @ValidStoreName
    private String name;

    @ValidCompanyRegistrationNumber
    private String companyRegistrationNumber;

    @ValidLatitude
    private Double latitude;

    @ValidLongitude
    private Double longitude;

    @ValidZipCode
    private String zipCode;

    @NotNull
    private String fullAddress;
}
