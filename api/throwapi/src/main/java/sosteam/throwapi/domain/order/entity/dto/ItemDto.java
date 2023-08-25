package sosteam.throwapi.domain.order.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Value;

import java.util.UUID;

@Value
public class ItemDto {
    private UUID id;
    private String name;
    private Long price;
    private int stockQuantity;

    @QueryProjection
    public ItemDto(UUID id, String name, Long price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
