package sosteam.throwapi.domain.order.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sosteam.throwapi.domain.order.entity.Dto.GifticonCreateDto;
import sosteam.throwapi.domain.order.entity.Gifticon;
import sosteam.throwapi.domain.order.entity.Item;
import sosteam.throwapi.domain.user.entity.dto.user.UserInfoDto;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(SpringRunner.class)
@Transactional
public class ReceiptCreateServiceTest {
    @Autowired
    private ReceiptCreateService receiptCreateService;

    @Autowired
    private ItemCreateService itemCreateService;

    @Autowired
    private ItemSearchService itemSearchService ;

    @Test
    public void purchase() throws Exception{
        String testProductName = "Test Product";
        String testTemplateToken = "TestToken";

        log.debug("PURCHASE START");
        //Item 생성
        Item item=new Item(testTemplateToken,testProductName,"스타벅스","https://www.123456","https://www.1234567","https://www.12345678",(long)5000);
        itemCreateService.createItem(item);
        log.debug("ITEM CREATE");

        // when
        log.debug("SEARCH TEMPLATETOKEN");
        String searchTemplateTokenByProductName = itemSearchService.searchTTByProductName(testProductName);
        log.debug("GIFTICON AND RECEIPT CREATE");
        UserInfoDto userInfoDto=new UserInfoDto("1234");

        GifticonCreateDto gifticonCreateDto=new GifticonCreateDto(searchTemplateTokenByProductName,item,userInfoDto);
        Optional<Gifticon> gifticon = receiptCreateService.createGifticonAndReceipt(gifticonCreateDto);

        // then
        assertEquals("SEARCH TEMPLATETOKEN",searchTemplateTokenByProductName,testTemplateToken);
        assertEquals("GIFTICON CREATE",gifticon.get().getGiftTraceId(),"123456");

    }
}