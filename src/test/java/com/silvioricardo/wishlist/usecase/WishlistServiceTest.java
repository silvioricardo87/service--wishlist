package com.silvioricardo.wishlist.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.silvioricardo.wishlist.domain.Cliente;
import com.silvioricardo.wishlist.domain.Produto;
import com.silvioricardo.wishlist.domain.repository.ClienteRepository;
import com.silvioricardo.wishlist.domain.repository.ProdutoRepository;
import com.silvioricardo.wishlist.usecase.exception.BusinessException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
class WishlistServiceTest {

  @Mock
  private ClienteRepository clienteRepository;

  @Mock
  private ProdutoRepository produtoRepository;

  @MockBean
  private WishlistService wishlistService;

  private final Cliente cliente = new Cliente();
  private final Produto produto = new Produto();

  @BeforeEach
  void criaMockCliente() {
    cliente.setId("a1b2c3d4e5");
    cliente.setNome("João da Silva");
    cliente.setEmail("email@email.com");
    cliente.setCpf("61001347056");
  }

  @BeforeEach
  void criaMockProduto() {
    produto.setId("a6b7c8d9e0");
    produto.setNome("iPhone 14");
    produto.setDescricao("iPhone 14 128GB");
    produto.setPreco(6000.00);
    produto.setCategoria("Celular");
  }

  @Test
  void adicionarProdutoNaWishlist() {
    when(clienteRepository.findById(anyString())).thenReturn(Optional.of(cliente));
    when(produtoRepository.findById(anyString())).thenReturn(Optional.of(produto));
    when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

    wishlistService = new WishlistService(clienteRepository, produtoRepository);

    Cliente clienteAtualizado = wishlistService.adicionarProdutoNaWishlist("a1b2c3d4e5", "a6b7c8d9e0");

    assertFalse(clienteAtualizado.getWishlist().isEmpty());
  }

  @Test
  void adicionarProdutoNaWishlistJaExiste() {
    when(clienteRepository.findById(anyString())).thenReturn(Optional.of(cliente));
    when(produtoRepository.findById(anyString())).thenReturn(Optional.of(produto));
    when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

    wishlistService = new WishlistService(clienteRepository, produtoRepository);

    wishlistService.adicionarProdutoNaWishlist("a1b2c3d4e5", "a6b7c8d9e0");

    assertThrows(BusinessException.class, () -> wishlistService.adicionarProdutoNaWishlist("a1b2c3d4e5", "a6b7c8d9e0"));
  }

  @Test
  void removerProdutoDaWishlist() {
    List<Produto> wishlist = new ArrayList<>(List.of(produto));
    cliente.setWishlist(wishlist);

    when(clienteRepository.findById(anyString())).thenReturn(Optional.of(cliente));
    when(produtoRepository.findById(anyString())).thenReturn(Optional.of(produto));
    when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

    wishlistService = new WishlistService(clienteRepository, produtoRepository);

    Cliente clienteAtualizado = wishlistService.removerProdutoDaWishlist("a1b2c3d4e5", "a6b7c8d9e0");

    assertTrue(clienteAtualizado.getWishlist().isEmpty());
  }

  @Test
  void consultarWishlistDoCliente() {
    cliente.setWishlist(List.of(produto));

    when(clienteRepository.findById(anyString())).thenReturn(Optional.of(cliente));

    wishlistService = new WishlistService(clienteRepository, produtoRepository);

    List<Produto> wishlist = wishlistService.consultarWishlistDoCliente("a1b2c3d4e5");

    assertFalse(wishlist.isEmpty());
    assertEquals(produto.getId(), wishlist.get(0).getId());
  }

  @Test
  void produtoEstaNaWishlist() {
    cliente.setWishlist(List.of(produto));

    when(clienteRepository.findById(anyString())).thenReturn(Optional.of(cliente));
    when(produtoRepository.findById(anyString())).thenReturn(Optional.of(produto));

    wishlistService = new WishlistService(clienteRepository, produtoRepository);

    boolean estaNaWishlist = wishlistService.produtoEstaNaWishlist("a1b2c3d4e5", "a6b7c8d9e0");

    assertTrue(estaNaWishlist);
  }

  @Test
  void limparWishlistDoCliente() {
    when(clienteRepository.findById(anyString())).thenReturn(Optional.of(cliente));
    when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

    wishlistService = new WishlistService(clienteRepository, produtoRepository);

    wishlistService.limparWishlistDoCliente("a1b2c3d4e5");

    verify(clienteRepository).save(any(Cliente.class));
  }
}
