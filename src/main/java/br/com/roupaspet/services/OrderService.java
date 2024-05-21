package br.com.roupaspet.services;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.roupaspet.dtos.request.order.SaveOrderDTO;
import br.com.roupaspet.dtos.request.order.UpdateOrderDTO;
import br.com.roupaspet.dtos.response.order.OrderResponseDTO;
import br.com.roupaspet.models.Order;
import br.com.roupaspet.models.OrderItem;
import br.com.roupaspet.models.Product;
import br.com.roupaspet.models.User;
import br.com.roupaspet.repositories.OrderItemRepository;
import br.com.roupaspet.repositories.OrderRepository;
import br.com.roupaspet.repositories.ProductRepository;
import br.com.roupaspet.services.exceptions.APIException;
import jakarta.transaction.Transactional;

@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final OrderItemRepository orderItemRepository;

  public OrderService(
      OrderRepository orderRepository,
      ProductRepository productRepository,
      OrderItemRepository orderItemRepository //
  ) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
    this.orderItemRepository = orderItemRepository;
  }

  @Transactional
  public List<OrderResponseDTO> getAllByUser(User user) {
    return orderRepository.findAllByUser(user)
        .stream()
        .map(OrderResponseDTO::new)
        .toList();
  }

  @Transactional
  public OrderResponseDTO save(SaveOrderDTO dto, User user) {
    if (Objects.isNull(dto)) {
      throw new APIException("error");
    }

    var newOrder = new Order();

    newOrder.setUser(user);
    newOrder.setPaymentMethod(dto.paymentMethod());
    newOrder.setStatus(Order.OrderStatus.PENDING);

    dto.items().forEach(item -> {
      var product = productRepository.getReferenceById(item.productId());

      hasStock(product, item.quantity());

      var ordemItem = new OrderItem(newOrder, product, item.quantity(), product.getPrice() * item.quantity());
      newOrder.getItems().add(ordemItem);

      updateStock(product, item.quantity());
    });

    var orderCreated = orderRepository.save(newOrder);
    orderItemRepository.saveAll(orderCreated.getItems());

    return new OrderResponseDTO(orderCreated);
  }

  @Transactional
  public OrderResponseDTO update(UpdateOrderDTO dto, Long id) {
    if (Objects.isNull(dto) || Objects.isNull(id)) {
      throw new APIException("error");
    }

    var order = orderRepository.getReferenceById(id);
    order.update(dto);

    return new OrderResponseDTO(order);
  }

  private void updateStock(Product product, Integer quantity) {
    product.setStock(product.getStock() - quantity);
    productRepository.save(product);
  }

  private void hasStock(Product product, Integer quantity) {
    if (quantity > product.getStock()) {
      var message = String.format("Produto %s está com estoque insuficiente", product.getName());
      throw new APIException(message);
    }
  }
}
