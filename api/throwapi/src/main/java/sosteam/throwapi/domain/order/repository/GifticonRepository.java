package sosteam.throwapi.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sosteam.throwapi.domain.order.entity.Gifticon;

import java.util.UUID;

public interface GifticonRepository extends JpaRepository<Gifticon, UUID> {
}
