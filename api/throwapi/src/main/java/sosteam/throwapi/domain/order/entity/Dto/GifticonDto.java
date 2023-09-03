package sosteam.throwapi.domain.order.entity.Dto;

import sosteam.throwapi.domain.order.controller.response.GifticonResponse;
import sosteam.throwapi.domain.order.entity.Item;

import java.util.Set;

public class GifticonDto {
    public static GifticonResponse from(Item item, Set<GifticonResponse.GifticonInfo> gifticonInfos) {
        GifticonResponse response = new GifticonResponse();
        response.setProductName(item.getProductName());
        response.setBrandName(item.getBrandName());
        response.setBrandImageUrl(item.getBrandImageUrl());
        response.setProductImageUrl(item.getProductImageUrl());
        response.setProductThumbImageUrl(item.getProductThumbImageUrl());
        response.setPrice(item.getPrice());
        response.setGifticons(gifticonInfos);
        return response;
    }
}
