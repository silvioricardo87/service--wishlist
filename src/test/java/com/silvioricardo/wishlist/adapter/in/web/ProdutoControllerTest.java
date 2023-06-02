package com.silvioricardo.wishlist.adapter.in.web;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silvioricardo.wishlist.adapter.in.web.dto.ProdutoDto;
import com.silvioricardo.wishlist.adapter.in.web.dto.ProdutoUpdateDto;
import com.silvioricardo.wishlist.adapter.in.web.mapper.ProdutoMapper;
import com.silvioricardo.wishlist.domain.Produto;
import com.silvioricardo.wishlist.usecase.ProdutoService;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProdutoService produtoService;

  @MockBean
  private ProdutoMapper produtoMapper;

  @Autowired
  private ObjectMapper objectMapper;

  private final Produto produto = new Produto();
  private final ProdutoDto produtoDto = new ProdutoDto();
  private final String X_API_KEY_VALUE = "teste";

  void criaMockProduto(){
    produto.setId("a1b2c3d4e5");
    produto.setNome("iPhone 14 Pro");
    produto.setDescricao("iPhone 14 Pro 256GB");
    produto.setPreco(6499.99);
    produto.setCategoria("Celular");
  }

  void criaMockProdutoDto(){
    produtoDto.setNome("iPhone 14 Pro");
    produtoDto.setDescricao("iPhone 14 Pro 256GB AZUL");
    produtoDto.setPreco(5999.99);
    produtoDto.setCategoria("Smartphone");
  }

  @Test
  void deveCriarProduto() throws Exception {
    criaMockProduto();
    criaMockProdutoDto();

    when(produtoMapper.toDomain(produtoDto)).thenReturn(produto);
    when(produtoService.salvarProduto(produto)).thenReturn(produto);

    mockMvc.perform(post("/produtos")
            .header("X-API-KEY", X_API_KEY_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(produtoDto)))
        .andExpect(status().isCreated());

    verify(produtoMapper).toDomain(produtoDto);
    verify(produtoService).salvarProduto(produto);
    verify(produtoMapper).toDto(produto);
  }

  @Test
  void deveAtualizarProduto() throws Exception {
    criaMockProduto();

    ProdutoUpdateDto produtoUpdateDto = new ProdutoUpdateDto();
    produtoUpdateDto.setDescricao("iPhone 14 Pro 256GB AZUL");
    produtoUpdateDto.setPreco(5899.99);
    produtoUpdateDto.setCategoria("Smartphone");

    when(produtoMapper.toDomain(produtoUpdateDto)).thenReturn(produto);
    when(produtoService.atualizarProduto(produto.getId(), produto)).thenReturn(produto);

    mockMvc.perform(put("/produtos/" + produto.getId())
            .header("X-API-KEY", X_API_KEY_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(produtoUpdateDto)))
        .andExpect(status().isOk());

    verify(produtoMapper, times(1)).toDomain(produtoUpdateDto);
    verify(produtoService, times(1)).atualizarProduto(produto.getId(), produto);
    verify(produtoMapper, times(1)).toDto(produto);
  }

  @Test
  void deveObterProduto() throws Exception {
    criaMockProduto();
    when(produtoService.obterProduto(produto.getId())).thenReturn(Optional.of(produto));

    mockMvc.perform(get("/produtos/" + produto.getId())
            .header("X-API-KEY", X_API_KEY_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  void deveObterTodosOsProdutos() throws Exception {
    criaMockProduto();
    when(produtoService.obterTodosProdutos()).thenReturn(Arrays.asList(produto, produto));

    mockMvc.perform(get("/produtos")
            .header("X-API-KEY", X_API_KEY_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  void deveDeletarProduto() throws Exception {
    criaMockProduto();
    doNothing().when(produtoService).deletarProduto(produto.getId());

    mockMvc.perform(delete("/produtos/" + produto.getId())
            .header("X-API-KEY", X_API_KEY_VALUE))
        .andExpect(status().isNoContent());
  }

  @Test
  void deveRetornarNaoAutorizadoAoEnviarChaveNulaOuInvalida() throws Exception {
    criaMockProduto();

    mockMvc.perform(delete("/produtos/" + produto.getId()))
        .andExpect(status().isUnauthorized());
  }
}
