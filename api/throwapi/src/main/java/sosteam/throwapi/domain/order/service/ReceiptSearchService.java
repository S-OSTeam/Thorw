package sosteam.throwapi.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sosteam.throwapi.domain.order.entity.Receipt;
import sosteam.throwapi.domain.order.repository.repo.ReceiptRepository;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiptSearchService {
    private final ReceiptRepository receiptRepository;

    public Set<Receipt> searchAllReceipts() {
        log.debug("RECEIPTS SEARCH");
        return new HashSet<>(receiptRepository.findAll());
    }

    public Set<Receipt> searchUserReceipts(UUID userId){
        log.debug("RECEIPTS SEARCH BY USER ID");
        return new HashSet<>(receiptRepository.searchByUserId(userId).get());
    }
}
