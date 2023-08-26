package sosteam.throwapi.domain.order.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Value;

import java.util.UUID;

@Value
public class ItemDto {
    private String name;
    private Long price;
    private int stockQuantity;

    @QueryProjection
    public ItemDto(String name, Long price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
