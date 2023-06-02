package com.silvioricardo.wishlist.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.silvioricardo.wishlist.domain.Produto;
import com.silvioricardo.wishlist.domain.repository.ProdutoRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

  @Mock
  private ProdutoRepository produtoRepository;

  @MockBean
  private ProdutoService produtoService;

  private final Produto produto = new Produto();

  @BeforeEach
  void criaMockProduto() {
    produto.setId("a1b2c3d4e5");
    produto.setNome("iPhone 14");
    produto.setDescricao("iPhone 14 128GB");
    produto.setPreco(6000.00);
    produto.setCategoria("Celular");
  }

  @Test
  void salvarProduto() {
    when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

    produtoService = new ProdutoService(produtoRepository);

    Produto savedProduto = produtoService.salvarProduto(produto);

    assertEquals(produto.getId(), savedProduto.getId());
  }

  @Test
  void atualizarProduto() {
    produto.setDescricao("iPhone 14 256GB Azul");

    when(produtoRepository.findById(anyString())).thenReturn(Optional.of(produto));
    when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

    produtoService = new ProdutoService(produtoRepository);

    Produto updatedProduto = produtoService.atualizarProduto("a1b2c3d4e5", produto);

    assertEquals(produto.getId(), updatedProduto.getId());
    assertEquals(produto.getDescricao(), updatedProduto.getDescricao());
  }

  @Test
  void obterProduto() {
    when(produtoRepository.findById(anyString())).thenReturn(Optional.of(produto));

    produtoService = new ProdutoService(produtoRepository);

    Optional<Produto> obtainedProduto = produtoService.obterProduto("a1b2c3d4e5");

    assertTrue(obtainedProduto.isPresent());
    assertEquals(produto.getId(), obtainedProduto.get().getId());
  }

  @Test
  void obterTodosProdutos() {
    when(produtoRepository.findAll()).thenReturn(List.of(produto));

    produtoService = new ProdutoService(produtoRepository);

    List<Produto> produtos = produtoService.obterTodosProdutos();

    assertFalse(produtos.isEmpty());
    assertEquals(produto.getId(), produtos.get(0).getId());
  }

  @Test
  void deletarProduto() {
    when(produtoRepository.findById(anyString())).thenReturn(Optional.of(produto));
    doNothing().when(produtoRepository).delete(any(Produto.class));

    produtoService = new ProdutoService(produtoRepository);

    assertDoesNotThrow(() -> produtoService.deletarProduto("a1b2c3d4e5"));
  }
}
