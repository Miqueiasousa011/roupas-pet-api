package br.com.roupaspet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.roupaspet.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
