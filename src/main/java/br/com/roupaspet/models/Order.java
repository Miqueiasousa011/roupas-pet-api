package br.com.roupaspet.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.roupaspet.dtos.request.order.UpdateOrderDTO;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "id.order")
    private Set<OrderItem> items = new HashSet<>();

    private Double shippingCost;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Double getTotal() {
        return items.stream().mapToDouble(OrderItem::getSubTotal).sum() + shippingCost;
    }

    public void update(UpdateOrderDTO dto) {
        this.status = dto.status();
    }

    public enum OrderStatus {
        PENDING, DELIVERED, CANCELED
    }

    public enum PaymentMethod {
        CREDIT_CARD, PIX
    }
}
