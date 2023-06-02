package com.silvioricardo.wishlist.domain.repository;

import com.silvioricardo.wishlist.domain.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
  Cliente save(Cliente cliente);
  Optional<Cliente> findById(String id);
  void delete(Cliente cliente);
  List<Cliente> findAll();
}
