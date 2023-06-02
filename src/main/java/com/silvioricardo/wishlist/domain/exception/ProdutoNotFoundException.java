package com.silvioricardo.wishlist.domain.exception;

public class ProdutoNotFoundException extends RuntimeException {
  public ProdutoNotFoundException(String id) {
    super("Produto não encontrado: " + id);
  }
}
