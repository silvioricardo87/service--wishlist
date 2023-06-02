package com.silvioricardo.wishlist.adapter.in.web;

import com.silvioricardo.wishlist.domain.Cliente;
import com.silvioricardo.wishlist.domain.Produto;
import com.silvioricardo.wishlist.usecase.WishlistService;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WishlistController implements WishlistContract {
  private final WishlistService wishlistService;

  public WishlistController(WishlistService wishlistService) {
    this.wishlistService = wishlistService;
  }

  @Override
  public Cliente adicionarProdutoNaWishlist(String clienteId, String produtoId) {
    return wishlistService.adicionarProdutoNaWishlist(clienteId, produtoId);
  }

  @Override
  public Cliente removerProdutoDaWishlist(String clienteId, String produtoId) {
    return wishlistService.removerProdutoDaWishlist(clienteId, produtoId);
  }

  @Override
  public List<Produto> consultarWishlistDoCliente(String clienteId) {
    return wishlistService.consultarWishlistDoCliente(clienteId);
  }

  @Override
  public Boolean produtoEstaNaWishlist(String clienteId, String produtoId) {
    return wishlistService.produtoEstaNaWishlist(clienteId, produtoId);
  }

  @Override
  public void limpaWishlistDoCliente(String clienteId){
    wishlistService.limparWishlistDoCliente(clienteId);
  }
}
