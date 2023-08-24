package sosteam.throwapi.domain.order.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
public class ItemDto {
    private UUID id;
    private String name;
    private Long price;

    @QueryProjection
    public ItemDto(UUID id, String name, Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
