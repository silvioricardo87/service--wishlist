package com.silvioricardo.wishlist.domain.exception;

public class ClienteNotFoundException extends RuntimeException {
  public ClienteNotFoundException(String id) {
    super("Cliente não encontrado: " + id);
  }
}
