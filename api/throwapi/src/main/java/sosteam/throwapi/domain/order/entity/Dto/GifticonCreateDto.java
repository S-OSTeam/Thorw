package sosteam.throwapi.domain.order.entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sosteam.throwapi.domain.order.entity.Item;
import sosteam.throwapi.domain.user.entity.dto.user.UserInfoDto;

@Getter
@AllArgsConstructor
public class GifticonCreateDto {
    private String templateToken;
    private Item item;
    private UserInfoDto userInfoDto;
}
