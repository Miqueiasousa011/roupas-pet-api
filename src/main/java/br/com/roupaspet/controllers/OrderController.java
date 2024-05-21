package br.com.roupaspet.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.roupaspet.dtos.request.order.SaveOrderDTO;
import br.com.roupaspet.dtos.request.order.UpdateOrderDTO;
import br.com.roupaspet.dtos.response.order.OrderResponseDTO;
import br.com.roupaspet.helpers.GetUserFromTokenHelper;
import br.com.roupaspet.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/orders")
public class OrderController {
  private final OrderService orderService;
  private final GetUserFromTokenHelper getUserFromTokenHelper;

  public OrderController(OrderService orderService, GetUserFromTokenHelper getUserFromTokenHelper) {
    this.orderService = orderService;
    this.getUserFromTokenHelper = getUserFromTokenHelper;
  }

  @GetMapping
  public ResponseEntity<List<OrderResponseDTO>> getAllByUser(HttpServletRequest request) {
    var user = getUserFromTokenHelper.getUserFromToken(request);
    return ResponseEntity.ok(orderService.getAllByUser(user));
  }

  @PostMapping
  public ResponseEntity<Object> save(@RequestBody SaveOrderDTO dto, HttpServletRequest request) {
    var user = getUserFromTokenHelper.getUserFromToken(request);
    var order = orderService.save(dto, user);

    var uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(order.id()).toUri();

    return ResponseEntity.created(uri).body(order);
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderResponseDTO> update(@RequestBody UpdateOrderDTO dto, @PathVariable Long id) {
    var order = orderService.update(dto, id);
    return ResponseEntity.ok(order);
  }
}
