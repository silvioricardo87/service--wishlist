package com.silvioricardo.wishlist.domain.repository;

import com.silvioricardo.wishlist.domain.Produto;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepository {
  Produto save(Produto produto);
  Optional<Produto> findById(String id);
  void delete(Produto produto);
  List<Produto> findAll();
}

