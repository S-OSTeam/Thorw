package sosteam.throwapi.domain.user.controller.response;

import lombok.Data;

@Data
public class PWCheckResponse {
    private boolean PWCheck;

    public PWCheckResponse(boolean PWCheck){
        this.PWCheck = PWCheck;
    }
}
