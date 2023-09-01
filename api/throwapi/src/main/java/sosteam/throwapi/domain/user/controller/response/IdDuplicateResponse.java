package sosteam.throwapi.domain.user.controller.response;

import lombok.Data;

@Data
public class IdDuplicateResponse {
    private boolean idDup;

    public IdDuplicateResponse(boolean idDup){
        this.idDup = idDup;
    }
}
