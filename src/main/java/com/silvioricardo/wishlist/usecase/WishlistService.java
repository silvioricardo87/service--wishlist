package com.silvioricardo.wishlist.usecase;

import com.silvioricardo.wishlist.domain.Cliente;
import com.silvioricardo.wishlist.domain.Produto;
import com.silvioricardo.wishlist.domain.repository.ClienteRepository;
import com.silvioricardo.wishlist.domain.repository.ProdutoRepository;
import com.silvioricardo.wishlist.usecase.exception.BusinessException;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {
  private final ClienteRepository clienteRepository;
  private final ProdutoRepository produtoRepository;

  public WishlistService(ClienteRepository clienteRepository, ProdutoRepository produtoRepository) {
    this.clienteRepository = clienteRepository;
    this.produtoRepository = produtoRepository;
  }

  @CachePut(value = "wishlist", key = "#clienteId")
  public Cliente adicionarProdutoNaWishlist(String clienteId, String produtoId) {
    Cliente cliente = buscarCliente(clienteId);
    Produto produto = buscarProduto(produtoId);

    if(cliente.getWishlist().contains(produto)) {
      throw new BusinessException("O produto já está na wishlist.");
    }

    if (cliente.getWishlist().size() >= 20) {
      throw new BusinessException("A wishlist já atingiu o limite de 20 produtos.");
    }

    cliente.getWishlist().add(produto);
    return clienteRepository.save(cliente);
  }

  @CachePut(value = "wishlist", key = "#clienteId")
  public Cliente removerProdutoDaWishlist(String clienteId, String produtoId) {
    Cliente cliente = buscarCliente(clienteId);
    Produto produto = buscarProduto(produtoId);

    cliente.getWishlist().remove(produto);
    return clienteRepository.save(cliente);
  }

  @CachePut(value = "wishlist", key = "#clienteId")
  public List<Produto> consultarWishlistDoCliente(String clienteId) {
    Cliente cliente = buscarCliente(clienteId);
    return cliente.getWishlist();
  }

  @CachePut(cacheNames = "produtoNaWishlist", key = "#clienteId.concat(#produtoId)")
  public boolean produtoEstaNaWishlist(String clienteId, String produtoId) {
    Cliente cliente = buscarCliente(clienteId);
    Produto produto = buscarProduto(produtoId);

    return cliente.getWishlist().contains(produto);
  }

  @CacheEvict(value = "wishlist", key = "#clienteId")
  public void limparWishlistDoCliente(String clienteId) {
    Cliente cliente = buscarCliente(clienteId);
    cliente.getWishlist().clear();
    clienteRepository.save(cliente);
  }

  private Cliente buscarCliente(String id) {
    return clienteRepository.findById(id)
        .orElseThrow(() -> new BusinessException("Cliente não encontrado."));
  }

  private Produto buscarProduto(String id) {
    return produtoRepository.findById(id)
        .orElseThrow(() -> new BusinessException("Produto não encontrado."));
  }
}
