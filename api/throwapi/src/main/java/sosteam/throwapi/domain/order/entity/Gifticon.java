package sosteam.throwapi.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import sosteam.throwapi.global.entity.PrimaryKeyEntity;

import java.util.UUID;

@Getter
@Entity
public class Gifticon extends PrimaryKeyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @NotNull
    private String couponNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;
}
