package sosteam.throwapi.domain.order.entity.Dto;

import sosteam.throwapi.domain.order.controller.response.GifticonResponse;
import sosteam.throwapi.domain.order.controller.response.ReceiptResponse;
import sosteam.throwapi.domain.order.entity.Gifticon;
import sosteam.throwapi.domain.order.entity.Item;

public class ReceiptDto {
    public static ReceiptResponse from(Item item, Gifticon gifticon) {
        ReceiptResponse response = new ReceiptResponse();
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
