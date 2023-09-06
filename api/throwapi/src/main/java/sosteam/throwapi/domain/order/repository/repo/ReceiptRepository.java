package sosteam.throwapi.domain.order.repository.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sosteam.throwapi.domain.order.entity.Receipt;
import sosteam.throwapi.domain.order.repository.repoCustom.ReceiptCustomRepository;

import java.util.UUID;

public interface ReceiptRepository extends JpaRepository<Receipt, UUID>, ReceiptCustomRepository {
}
