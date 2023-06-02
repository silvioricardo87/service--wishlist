package com.silvioricardo.wishlist.usecase;

import com.silvioricardo.wishlist.domain.Produto;
import com.silvioricardo.wishlist.domain.repository.ProdutoRepository;
import com.silvioricardo.wishlist.usecase.exception.BusinessException;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

  private final ProdutoRepository produtoRepository;

  public ProdutoService(ProdutoRepository produtoRepository) {
    this.produtoRepository = produtoRepository;
  }

  @CachePut(value = "produto", key = "#produto.id")
  public Produto salvarProduto(Produto produto) {
    return produtoRepository.save(produto);
  }

  @CachePut(value = "produto", key = "#id")
  public Produto atualizarProduto(String id, Produto produto) {
    Produto produtoAtualizado = obterProduto(id)
        .orElseThrow(() -> new BusinessException("Produto não encontrado."));

    if(produto.getDescricao() != null)
      produtoAtualizado.setDescricao(produto.getDescricao());

    if(produto.getPreco() > 0)
      produtoAtualizado.setPreco(produto.getPreco());

    if(produto.getDescricao() != null)
      produtoAtualizado.setDescricao(produto.getDescricao());

    return produtoRepository.save(produtoAtualizado);
  }

  @Cacheable(value = "produto", key = "#id")
  public Optional<Produto> obterProduto(String id) {
    return produtoRepository.findById(id);
  }

  @Cacheable(value = "produtos")
  public List<Produto> obterTodosProdutos() {
    return produtoRepository.findAll();
  }

  @CacheEvict(value = "produto", key = "#id")
  public void deletarProduto(String id) {
    Produto produto = obterProduto(id)
        .orElseThrow(() -> new BusinessException("Produto não encontrado."));
    produtoRepository.delete(produto);
  }
}


