package sosteam.throwapi.domain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreSaveDto {
    private String name;
    private String companyRegisterNumber;
    private String secondPassword;
    private double latitude;
    private double longitude;
    private String zipCode;
    private String fullAddress;
}
