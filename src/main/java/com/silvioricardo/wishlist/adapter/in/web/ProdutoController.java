package com.silvioricardo.wishlist.adapter.in.web;

import com.silvioricardo.wishlist.adapter.in.web.dto.ProdutoDto;
import com.silvioricardo.wishlist.adapter.in.web.dto.ProdutoUpdateDto;
import com.silvioricardo.wishlist.adapter.in.web.mapper.ProdutoMapper;
import com.silvioricardo.wishlist.domain.Produto;
import com.silvioricardo.wishlist.usecase.ProdutoService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProdutoController implements ProdutoContract {

  private final ProdutoService produtoService;
  private final ProdutoMapper produtoMapper;

  public ProdutoController(ProdutoService produtoService, ProdutoMapper produtoMapper) {
    this.produtoService = produtoService;
    this.produtoMapper = produtoMapper;
  }

  @Override
  public ProdutoDto criarProduto(ProdutoDto produtoDto) {
    Produto produto = produtoMapper.toDomain(produtoDto);
    return produtoMapper.toDto(produtoService.salvarProduto(produto));
  }

  @Override
  public ProdutoDto atualizarProduto(String id, @Valid ProdutoUpdateDto produtoUpdateDto) {
    Produto produto = produtoMapper.toDomain(produtoUpdateDto);
    return produtoMapper.toDto(produtoService.atualizarProduto(id, produto));
  }

  @Override
  public Optional<Produto> obterProduto(String id) {
    return produtoService.obterProduto(id);
  }

  @Override
  public List<Produto> obterTodosProdutos() {
    return produtoService.obterTodosProdutos();
  }

  @Override
  public void deletarProduto(String id) {
    produtoService.deletarProduto(id);
  }
}
