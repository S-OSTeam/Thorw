package sosteam.throwapi.domain.store.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sosteam.throwapi.domain.store.validation.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreSaveRequest {
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
