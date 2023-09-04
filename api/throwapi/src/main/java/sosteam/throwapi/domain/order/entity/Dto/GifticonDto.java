package sosteam.throwapi.domain.order.entity.Dto;

import sosteam.throwapi.domain.order.controller.response.GifticonResponse;
import sosteam.throwapi.domain.order.entity.Gifticon;
import sosteam.throwapi.domain.order.entity.Item;

public class GifticonDto {
    public static GifticonResponse from(Item item, Gifticon gifticon) {
        GifticonResponse response = new GifticonResponse();
        response.setProductName(item.getProductName());
        response.setBrandName(item.getBrandName());
        response.setBrandImageUrl(item.getBrandImageUrl());
        response.setProductImageUrl(item.getProductImageUrl());
        response.setProductThumbImageUrl(item.getProductThumbImageUrl());
        response.setPrice(item.getPrice());
        response.setGifticonId(gifticon.getId());
        response.setGiftTraceId(gifticon.getGiftTraceId());
        return response;
    }
}
