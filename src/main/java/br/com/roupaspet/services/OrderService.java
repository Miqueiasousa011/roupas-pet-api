package br.com.roupaspet.services;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.roupaspet.dtos.request.category.SaveCategoryDTO;
import br.com.roupaspet.dtos.request.category.UpdateCategoryDTO;
import br.com.roupaspet.dtos.request.order.SaveOrderDTO;
import br.com.roupaspet.dtos.response.CategoryResponseDTO;
import br.com.roupaspet.dtos.response.order.OrderResponseDTO;
import br.com.roupaspet.models.Category;
import br.com.roupaspet.models.Order;
import br.com.roupaspet.models.OrderItem;
import br.com.roupaspet.models.Product;
import br.com.roupaspet.models.User;
import br.com.roupaspet.repositories.OrderRepository;
import br.com.roupaspet.repositories.ProductRepository;
import br.com.roupaspet.services.exceptions.APIException;
import jakarta.transaction.Transactional;

@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;

  public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
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

    // obj.setId(null);
    // obj.setInstante(new Date());
    // obj.setCliente(clienteService.find(obj.getCliente().getId()));
    // obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
    // obj.getPagamento().setPedido(obj);
    // if (obj.getPagamento() instanceof PagamentoComBoleto) {
    // PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
    // boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
    // }

    // obj = repo.save(obj);
    // pagamentoRepository.save(obj.getPagamento());
    // for (ItemPedido ip : obj.getItens()) {
    // ip.setDesconto(0.0);
    // ip.setProduto(produtoService.find(ip.getProduto().getId()));
    // ip.setPreco(ip.getProduto().getPreco());
    // ip.setPedido(obj);
    // }
    // itemPedidoRepository.saveAll(obj.getItens());

    var order = new Order();

    order.setUser(user);
    order.setPaymentMethod(dto.paymentMethod());
    order.setStatus(Order.OrderStatus.PENDING);

    dto.items().forEach(item -> {
      var product = productRepository.getReferenceById(item.productId());

      hasStock(product, item.quantity());

      order.getItems().add(new OrderItem(order, product, item.quantity(), product.getPrice() * item.quantity()));

      updateStock(product, item.quantity());
    });

    var orderCreated = orderRepository.save(order);

    return new OrderResponseDTO(orderCreated);
  }

  // @Transactional
  // public CategoryResponseDTO update(UpdateCategoryDTO dto, Long id) {
  // var category = categoryRepository.getReferenceById(id);
  // category.update(dto);

  // return new CategoryResponseDTO(category);
  // }

  // @Transactional
  // public void logicalDelete(Long id) {
  // var todo = categoryRepository.getReferenceById(id);
  // todo.deleteLogical();
  // }

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
