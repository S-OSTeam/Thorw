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

    public Optional<Receipt> getReceiptById(UUID receiptId) {
        log.debug("RECEIPT SEARCH");
        return receiptRepository.findById(receiptId);
    }

    public Set<Receipt> getAllReceipts() {
        log.debug("RECEIPTS SEARCH");
        return new HashSet<>(receiptRepository.findAll());
    }
}
