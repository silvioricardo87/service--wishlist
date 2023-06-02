package com.silvioricardo.wishlist.adapter.in.web;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.silvioricardo.wishlist.domain.Cliente;
import com.silvioricardo.wishlist.domain.Produto;
import com.silvioricardo.wishlist.usecase.WishlistService;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(WishlistController.class)
class WishlistControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private WishlistService wishlistService;

  private final Cliente cliente = new Cliente();
  private final Produto produto = new Produto();
  private final String X_API_KEY_VALUE = "teste";

  void criaMockCliente() {
    cliente.setId("a1b2c3d4e5");
    cliente.setNome("João da Silva");
    cliente.setEmail("email@email.com");
    cliente.setCpf("61001347056");
    cliente.setDataNascimento(LocalDate.parse("2000-01-01"));
  }

  void criaMockProduto() {
    produto.setId("a1b2c3d4e5");
    produto.setNome("iPhone 14 Pro");
    produto.setDescricao("iPhone 14 Pro 256GB");
    produto.setPreco(6499.99);
    produto.setCategoria("Celular");
  }

  @Test
  void deveAdicionarUmProdutoNaWishlist() throws Exception {
    criaMockCliente();
    criaMockProduto();

    when(wishlistService.adicionarProdutoNaWishlist(cliente.getId(), produto.getId())).thenReturn(
        cliente);

    mockMvc.perform(post("/clientes/" + cliente.getId() + "/wishlist/" + produto.getId())
            .header("X-API-KEY", X_API_KEY_VALUE))
        .andExpect(status().isOk());

    verify(wishlistService, times(1)).adicionarProdutoNaWishlist(cliente.getId(), produto.getId());
  }

  @Test
  void shouldRemoveProductFromWishlist() throws Exception {
    criaMockCliente();
    criaMockProduto();

    when(wishlistService.removerProdutoDaWishlist(cliente.getId(), produto.getId())).thenReturn(
        cliente);

    mockMvc.perform(delete("/clientes/" + cliente.getId() + "/wishlist/" + produto.getId())
            .header("X-API-KEY", X_API_KEY_VALUE))
        .andExpect(status().isOk());

    verify(wishlistService, times(1)).removerProdutoDaWishlist(cliente.getId(), produto.getId());
  }

  @Test
  void deveObterAWishlistDoCliente() throws Exception {
    criaMockCliente();

    Produto produto1 = new Produto();
    produto.setId("IdDoProduto1");

    Produto produto2 = new Produto();
    produto2.setId("IdDoProduto2");

    when(wishlistService.consultarWishlistDoCliente(cliente.getId())).thenReturn(
        Arrays.asList(produto1, produto2));

    mockMvc.perform(get("/clientes/" + cliente.getId() + "/wishlist")
            .header("X-API-KEY", X_API_KEY_VALUE))
        .andExpect(status().isOk());

    verify(wishlistService, times(1)).consultarWishlistDoCliente(cliente.getId());
  }

  @Test
  void deveConsultarProdutoNaWishlist() throws Exception {
    criaMockCliente();
    criaMockProduto();

    when(wishlistService.produtoEstaNaWishlist(cliente.getId(), produto.getId())).thenReturn(true);

    mockMvc.perform(get("/clientes/" + cliente.getId() + "/wishlist/" + produto.getId())
            .header("X-API-KEY", X_API_KEY_VALUE))
        .andExpect(status().isOk());

    verify(wishlistService, times(1)).produtoEstaNaWishlist(cliente.getId(), produto.getId());
  }

  @Test
  void deveLimparAWishlistDoCliente() throws Exception {
    criaMockCliente();
    doNothing().when(wishlistService).limparWishlistDoCliente(cliente.getId());

    mockMvc.perform(delete("/clientes/" + cliente.getId() + "/wishlist/clear")
            .header("X-API-KEY", X_API_KEY_VALUE))
        .andExpect(status().isOk());

    verify(wishlistService, times(1)).limparWishlistDoCliente(cliente.getId());
  }

  @Test
  void deveRetornarNaoAutorizadoAoEnviarChaveNulaOuInvalida() throws Exception {
    criaMockCliente();

    mockMvc.perform(post("/clientes/" + cliente.getId() + "/wishlist/" + produto.getId()))
        .andExpect(status().isUnauthorized());
  }

}
