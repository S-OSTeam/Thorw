package sosteam.throwapi.domain.order.repository.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sosteam.throwapi.domain.order.entity.Gifticon;
import sosteam.throwapi.domain.order.repository.repoCustom.GifticonCustomRepository;

import java.util.UUID;

public interface GifticonRepository extends JpaRepository<Gifticon, UUID>, GifticonCustomRepository {
}
