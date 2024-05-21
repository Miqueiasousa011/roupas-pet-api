package br.com.roupaspet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.roupaspet.models.User;
import br.com.roupaspet.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findAllByUser(User user);
}
