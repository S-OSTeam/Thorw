package sosteam.throwapi.domain.order.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sosteam.throwapi.domain.order.entity.Item;
import sosteam.throwapi.domain.order.repository.repo.ItemRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// @Slf4j
// @ExtendWith(MockitoExtension.class)
// public class ReceiptCreateServiceTest {
//     @InjectMocks
//     private ItemSearchService itemSearchService;
//     @Mock
//     private ItemCreateService itemCreateService;

//     @Mock
//     private ItemRepository itemRepository;

//     @Test
//     public void itemSearch() throws Exception {
//         String testProductName = "Test Product";
//         String testTemplateToken = "TestToken";

//         log.debug("PURCHASE START");
//         //Item 생성
//         Item item = new Item(testTemplateToken, testProductName, "스타벅스", "https://www.123456", "https://www.1234567", "https://www.12345678", (long) 5000);
//         when(itemCreateService.createItem(item)).thenReturn(item);
//         log.debug("ITEM CREATE");

//         // when
//         log.debug("SEARCH TEMPLATETOKEN");
//         String searchTemplateTokenByProductName = itemSearchService.searchTemplateTokenByProductName(testProductName);

//         // then
//         assertEquals("SEARCH TEMPLATETOKEN", searchTemplateTokenByProductName, testTemplateToken);

//     }
// }
