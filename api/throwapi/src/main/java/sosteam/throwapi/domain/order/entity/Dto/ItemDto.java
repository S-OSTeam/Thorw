package sosteam.throwapi.domain.order.entity.Dto;

import sosteam.throwapi.domain.order.controller.response.ItemResponse;
import sosteam.throwapi.domain.order.entity.Item;

public class ItemDto {
    public static ItemResponse toItemResponse(Item item) {
        ItemResponse response = new ItemResponse();
        response.setItemId(item.getId());
        response.setProductName(item.getProductName());
        response.setBrandName(item.getBrandName());
        response.setBrandImageUrl(item.getBrandImageUrl());
        response.setProductImageUrl(item.getProductImageUrl());
        response.setProductThumbImageUrl(item.getProductThumbImageUrl());
        response.setPrice(item.getPrice());
        return response;
    }
}
